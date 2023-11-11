package ExerciseOne

import ExerciseOne.Tables._
import footballTables.Tables.Helpgoalconsistence
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object SlickQueries {
  def apply(h2Driver: String, url: String): JdbcQueries = new JdbcQueries(h2Driver, url)
}

class SlickQueries(h2Driver: String, url: String) extends SimpleQueries {

  val username = "sa"
  val password = "1234"

  val db = Database.forURL(url, username, password, driver = h2Driver)

  override def close(): Future[Unit] = Future{
      db.close()
  }

  override def isConsistent(): Future[Boolean] = {
    val awayScoreInconsistentQuery = Helpgoalconsistence.filter(_.awayTeamScored)
      .groupBy(g => (g.gameId, g.awayScore))
      .map {
        case (gameId_awayScore, group) => (gameId_awayScore, group.length)
      }
      .filter(v => v._1._2 =!= v._2)
      .length

    val homeScoreInconsistentQuery = Helpgoalconsistence.filter(!_.awayTeamScored)
      .groupBy(g => (g.gameId, g.homeScore))
      .map {
        case (gameId_homeScore, group) => (gameId_homeScore, group.length)
      }
      .filter(v => v._1._2 =!= v._2)
      .length

    db.run((awayScoreInconsistentQuery === 0 && homeScoreInconsistentQuery === 0).result)
  }

  override def countGoals(name: String): Future[Int] = {
    val goalOfPlayer = Goal.filter(_.player === name)
    db.run(goalOfPlayer.length.result)
  }

  override def countRangeGoals(min: Int, max: Int): Future[Int] = {
    val gamesGoalInRange = Game.filter(currentGame =>
      currentGame.awayScore + currentGame.homeScore >= min && currentGame.awayScore + currentGame.homeScore <= max)

    db.run(gamesGoalInRange.length.result)
  }
}
