package ExersiceTwo

import redis.clients.jedis.Jedis
import scala.io.Source

object main {

  val host = "localhost"
  val port = 6379
  val jedis = new Jedis(host, port)

  def main(args: Array[String]): Unit = {

    for (line <- Source.fromFile("C:\\Users\\janin\\data\\data\\goalscorers.csv").getLines) {
      val cols = line.split(",").map(_.trim)
      jedis.hset("", cols(0), cols(1))
    }

    //look at hash
    val result = jedis.hget("1", "Date")

  }

  def close(): Unit = {
        jedis.close()
  }

}
