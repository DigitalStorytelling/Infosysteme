package ExersiceTwo

import redis.clients.jedis.params.ScanParams
import redis.clients.jedis.{Jedis, JedisPool}

import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.jdk.CollectionConverters.CollectionHasAsScala

object Queries {
  def apply(jedisPool: JedisPool): SimpleQueries = new Queries(jedisPool)
}

class Queries(jedisPool: JedisPool) extends SimpleQueries {

  override def close(): Future[Unit] = Future {
    jedisPool.close()
  }

  override def isConsistent(): Future[Boolean] = Future {
    val jedis = jedisPool.getResource
    val fieldsToRetrieveGoal = Seq("game_id", "team")
    val goals = getAllHashesWithPrefix(jedis, "goal:", fieldsToRetrieveGoal)

    // view is needed for mapValue
   val groupedGoals = goals
     .groupBy(goal => (goal("game_id"), goal("team")))
     .view
     .mapValues(_.size)
     .toMap

    // get from game the same data and save both
    val gameHashesPerGroup = groupedGoals.map { case ((gameId, team), score) =>
      val gameHashes = jedis.hmget(gameId, "away_team", "away_score", "home_score").asScala.toList
      if (team.equals(gameHashes(0))) {
        (true, score, gameHashes(1), gameHashes(2) )
      } else {
        (false, score, gameHashes(1), gameHashes(2))
      }
    }

    // score is from calculated by group goal
    // away_score and home_score from game
    val allInconsistencies = gameHashesPerGroup.filter { case (isAwayTeam, goalScore, awayScoreGame, homeScoreGame) =>
      if (isAwayTeam) {
        goalScore != awayScoreGame.toInt
      } else {
        goalScore != homeScoreGame.toInt
      }
    }

    allInconsistencies.isEmpty
  }

  override def countGoals(name: String): Future[Int] = Future {
    val jedis = jedisPool.getResource
    val fieldsToRetrieve = Seq("scorer")
    val hashes = getAllHashesWithPrefix(jedis, "goal:", fieldsToRetrieve)

    val hashResult = hashes.filter(currentData => currentData("scorer") == name)

    hashResult.length
  }

  override def countRangeGoals(min: Int, max: Int): Future[Int] = Future {
    val jedis = jedisPool.getResource
    val fieldsToRetrieve = Seq("home_score", "away_score")
    val hashes = getAllHashesWithPrefix(jedis, "game:", fieldsToRetrieve)

    val hashResult = hashes.filter(currentData => {
      val awayScore = currentData("away_score").toInt
      val homeScore = currentData("home_score").toInt
      val scoreSum = awayScore + homeScore
      scoreSum >= min && scoreSum <= max
    })

    hashResult.length
  }

  @tailrec
  private def getAllHashesWithPrefix(jedis: Jedis, prefix: String, fields: Seq[String], cursor: String = "0", hashes: List[Map[String, String]] = List.empty): List[Map[String, String]] = {
    val scanResult = jedis.scan(cursor, new ScanParams().`match`(prefix + "*").count(1000))
    val keys = scanResult.getResult.asScala.toList
    val newHashes = keys.map(key => {
      val hash = jedis.hmget(key, fields: _*).asScala
      fields.zip(hash).toMap
    })
    val updatedHashes = hashes ++ newHashes
    val nextCursor = scanResult.getCursor

    if (nextCursor.equals("0")) {
      updatedHashes
    }
    else {
      getAllHashesWithPrefix(jedis, prefix, fields, nextCursor, updatedHashes)
    }
  }
}
