package ExersiceTwo

import redis.clients.jedis.{JedisPool, JedisPoolConfig}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.StdIn

object Main {
  private val host = "localhost"
  private val port = 6379
  private val poolConfig = new JedisPoolConfig()
  poolConfig.setMaxTotal(10)
  poolConfig.setMaxIdle(5)

  private val jedis = new JedisPool(poolConfig, host, port)

  def main(args: Array[String]): Unit = {

    val dataLoader = LoadData(port, host)
    dataLoader.loadAll()

    System.out.println("~~ Press any key to close ~~")
    execute(Queries(jedis), "Gerd Müller", 15, 20)
    StdIn.readLine
  }

  private def execute(redis: SimpleQueries, player: String, min: Int, max: Int): Unit = {

    val countGoals = redis.countGoals(player).map { goals =>
      println(s"number of goals by $player: $goals")
    }

    val rangeGoals = redis.countRangeGoals(min, max).map { count =>
      println(s"number of games with at least $min and at most $max goals: $count")
    }

    val isConsistent = redis.isConsistent().map { answer =>
      if (answer) println("no inconsistent records in table 'goalscorers' found...")
      else println("INCONSISTENT RECORDS in table 'goalscorers' found...")
    }

    Future.sequence(Seq(countGoals, rangeGoals, isConsistent)).onComplete(_ => redis.close())
  }
}