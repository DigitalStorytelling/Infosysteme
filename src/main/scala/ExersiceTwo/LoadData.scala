package ExersiceTwo

import redis.clients.jedis.params.ScanParams
import redis.clients.jedis.{Jedis, Pipeline}

import java.io.{BufferedReader, FileReader}
import scala.annotation.tailrec
import scala.io.Source
import scala.jdk.CollectionConverters.CollectionHasAsScala
import scala.util.Using

object LoadData {

  def apply(port: Int, host: String) = new LoadData(port, host)

  class LoadData(port: Int, host: String) {
    private val jedis = new Jedis(host, port)

    def loadAll(): Unit = {

      val dbSize = jedis.dbSize
      println(dbSize)

      loadDataCSVAsHash("C:\\Users\\janin\\data\\data\\results.csv", "game")
      loadDataCSVAsHash("C:\\Users\\janin\\data\\data\\goalscorers.csv", "goal")
      loadDataCSVAsHash("C:\\Users\\janin\\data\\data\\shootouts.csv", "shootout")

      //also deletes date, away, home team
      addConnectionBetweenHashes("game:", "shootout:", "game_id")
      addConnectionBetweenHashes("game:", "goal:", "game_id")

      println(jedis.dbSize)
      close()
    }

    @tailrec
    private def getAllHashesWithPrefix(prefix: String, fields: Seq[String], cursor: String = "0", hashes: List[Map[String, String]] = List.empty): List[Map[String, String]] = {
      val scanResult = jedis.scan(cursor, new ScanParams().`match`(prefix + "*").count(1000))
      val keys = scanResult.getResult.asScala.toList
      val newHashes = keys.map(key => {
        val hash = jedis.hmget(key, fields: _*).asScala
        val fieldMap = fields.zip(hash).toMap
        // need key for later to make the connections
        fieldMap + ("key" -> key)
      })
      val updatedHashes = hashes ++ newHashes
      val nextCursor = scanResult.getCursor

      if (nextCursor.equals("0")) updatedHashes
      else getAllHashesWithPrefix(prefix, fields, nextCursor, updatedHashes)
    }

    @tailrec
    private def deleteFields(prefix: String, fields: Seq[String], cursor: String = "0"): Unit = {
      val scanResult = jedis.scan(cursor, new ScanParams().`match`(prefix + "*").count(1000))
      val keys = scanResult.getResult.asScala.toList

      for(key <- keys){
        jedis.hdel(key, fields: _*)
      }

      val nextCursor = scanResult.getCursor

      if (!nextCursor.equals("0")) {
        deleteFields(prefix, fields, nextCursor)
      }
    }

    private def loadDataCSVAsHash(path: String, prefix: String): Unit = {
      val src: Seq[String] =
        Using.resource(new BufferedReader(new FileReader(path))) { reader =>
          Iterator.unfold(())(_ => Option(reader.readLine()).map(_ -> ())).toList.drop(1)
        }

      val headerList: Array[String] = Using.resource(Source.fromFile(path)) { src =>
        src.getLines().take(1).flatMap(data => data.split(",").map(_.trim)).toArray
      }

      for ((currentLine, currentID) <- src.zipWithIndex) {
        val pipeline: Pipeline = jedis.pipelined()
        val cols = currentLine.split(",").map(_.trim)

        for ((category, colValue) <- headerList.zip(cols)) {
          pipeline.hset(s"$prefix:$currentID", s"$category", s"$colValue")
        }
        pipeline.sync()
      }
    }

    // Base => Data which is referenced,
    // add => data which gets a new field
    private def addConnectionBetweenHashes(prefixBase: String, prefixAdd: String, nameForField: String): Unit = {
      val fieldsToRetrieve = Seq("home_team", "away_team", "date")
      val addHashes = getAllHashesWithPrefix(prefixAdd, fieldsToRetrieve)
      val baseHashes = getAllHashesWithPrefix(prefixBase, fieldsToRetrieve)

      for(currentDataAdd <- addHashes) {
        val matches = baseHashes.find { currentDataBase =>
          currentDataAdd("away_team") == currentDataBase("away_team") &&
            currentDataAdd("home_team") == currentDataBase("home_team") &&
            currentDataAdd("date") == currentDataBase("date")
      }

        matches match {
          case Some(currentMatch) =>
            jedis.hset(currentDataAdd("key"), nameForField, currentMatch("key"))
          case None =>
        }
      }

      val fieldsToDelete = Seq("home_team", "away_team", "date")
      deleteFields(prefixAdd, fieldsToDelete)
    }

    private def close(): Unit = {
      jedis.close()
    }

  }
}