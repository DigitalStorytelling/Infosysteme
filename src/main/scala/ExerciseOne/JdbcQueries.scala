package ExerciseOne

import java.sql.DriverManager
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object JdbcQueries {
  def apply(h2Driver: String, url: String): JdbcQueries = new JdbcQueries(h2Driver, url)
}

class JdbcQueries(h2Driver: String, url: String) extends SimpleQueries {

  val username = "sa"
  val password = "1234"
  val connection = DriverManager.getConnection(url, username, password)

  override def countGoals(name: String): Future[Int] = Future {
    // preparedStatement um "escape single quotes" sehe Name: A'ala Hubail
    val preparedSelect = connection.prepareStatement("Select count(*) goals from Goal where player = ?")
    preparedSelect.setString(1, name)

    val resultSet = preparedSelect.executeQuery
    if (resultSet.next) resultSet.getInt("goals") else 0
  }

  override def isConsistent(): Future[Boolean] = Future {
    val query = "SELECT game_id, AWAY_SCORE, AWAY_TEAM ,HOME_TEAM ,HOME_SCORE FROM HELPGOALCONSISTENCE WHERE away_team_scored = true " +
      "GROUP BY game_id, AWAY_SCORE HAVING AWAY_SCORE != COUNT(*) UNION ALL SELECT game_id, AWAY_SCORE, AWAY_TEAM ,HOME_TEAM ,HOME_SCORE " +
      "FROM HELPGOALCONSISTENCE WHERE AWAY_TEAM_SCORED = false group by game_id, home_score HAVING home_SCORE != COUNT(*)"

    val statement = connection.createStatement;
    val resultSet = statement.executeQuery(query)

    if (resultSet.next) false else true
  }

  override def countRangeGoals(min: Int, max: Int): Future[Int] = Future {
    val query = s"SELECT COUNT(*) AS total_games from game WHERE (HOME_SCORE + AWAY_SCORE ) BETWEEN $min AND $max"
    val statement = connection.createStatement;

    val resultSet = statement.executeQuery(query)
    if(resultSet.next) resultSet.getInt("total_games") else 0
  }

  override def close(): Future[Unit] = Future {
    connection.close()
  }
}