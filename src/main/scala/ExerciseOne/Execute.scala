package ExerciseOne

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.StdIn

object Execute {

  private val url = "jdbc:h2:/localhost/~/football"
  private val h2Driver = "org.h2.Driver"

  def main(args: Array[String]): Unit = {
    println("~~ Press any key to end ~~")

    for (queries <- List(JdbcQueries(h2Driver, url), SlickQueries(h2Driver, url))) {
      execute(queries, "Gerd Müller", 15, 20)
    }

    StdIn.readLine
  }

  private def execute(query: SimpleQueries, player: String, min: Int, max: Int): Unit = {
    val countGoalQuery = query.countGoals(player).map { goals =>
      println(s"number of goals by $player: $goals")
    }

    val countRangeGoalQuery = query.countRangeGoals(min, max).map { games =>
      println(s"number of games with at least $games Spiele mit zwischen $min und $max Toren")
    }

    val isConsistentQuery = query.isConsistent.map { isConsistent =>
      if (isConsistent) println("no inconsistent records in table 'goalscorers' found...")
      else println("INCONSISTENT RECORDS in table 'goalscorers' found...")
    }

    Future.sequence(Seq(countGoalQuery, countRangeGoalQuery, isConsistentQuery)).onComplete(_ => query.close())
  }
}
