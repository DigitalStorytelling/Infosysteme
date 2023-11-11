package ExerciseOne

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends Tables {
  val profile = slick.jdbc.H2Profile
}

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(Country.schema, Game.schema, Goal.schema, Goalscorers.schema, Results.schema, Shootout.schema, Shootouts.schema, Team.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Country
   *  @param id Database column ID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param countryName Database column COUNTRY_NAME SqlType(CHARACTER VARYING), Length(255,true) */
  case class CountryRow(id: Int, countryName: Option[String])
  /** GetResult implicit for fetching CountryRow objects using plain SQL queries */
  implicit def GetResultCountryRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[CountryRow] = GR{
    prs => import prs._
    CountryRow.tupled((<<[Int], <<?[String]))
  }
  /** Table description of table COUNTRY. Objects of this class serve as prototypes for rows in queries. */
  class Country(_tableTag: Tag) extends profile.api.Table[CountryRow](_tableTag, "COUNTRY") {
    def * = (id, countryName).<>(CountryRow.tupled, CountryRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), countryName)).shaped.<>({r=>import r._; _1.map(_=> CountryRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INTEGER), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column COUNTRY_NAME SqlType(CHARACTER VARYING), Length(255,true) */
    val countryName: Rep[Option[String]] = column[Option[String]]("COUNTRY_NAME", O.Length(255,varying=true))
  }
  /** Collection-like TableQuery object for table Country */
  lazy val Country = new TableQuery(tag => new Country(tag))

  /** Entity class storing rows of table Game
   *  @param date Database column DATE SqlType(DATE)
   *  @param homeScore Database column HOME_SCORE SqlType(INTEGER)
   *  @param awayScore Database column AWAY_SCORE SqlType(INTEGER)
   *  @param tournament Database column TOURNAMENT SqlType(CHARACTER VARYING), Length(1000000000,true)
   *  @param city Database column CITY SqlType(CHARACTER VARYING), Length(1000000000,true)
   *  @param neutral Database column NEUTRAL SqlType(BOOLEAN)
   *  @param id Database column ID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param homeTeamId Database column HOME_TEAM_ID SqlType(INTEGER)
   *  @param awayTeamId Database column AWAY_TEAM_ID SqlType(INTEGER)
   *  @param shootoutId Database column SHOOTOUT_ID SqlType(INTEGER)
   *  @param countryId Database column COUNTRY_ID SqlType(INTEGER) */
  case class GameRow(date: Option[java.sql.Date], homeScore: Option[Int], awayScore: Option[Int], tournament: Option[String], city: Option[String], neutral: Option[Boolean], id: Int, homeTeamId: Option[Int], awayTeamId: Option[Int], shootoutId: Option[Int], countryId: Option[Int])
  /** GetResult implicit for fetching GameRow objects using plain SQL queries */
  implicit def GetResultGameRow(implicit e0: GR[Option[java.sql.Date]], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[Option[Boolean]], e4: GR[Int]): GR[GameRow] = GR{
    prs => import prs._
    GameRow.tupled((<<?[java.sql.Date], <<?[Int], <<?[Int], <<?[String], <<?[String], <<?[Boolean], <<[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int]))
  }
  /** Table description of table GAME. Objects of this class serve as prototypes for rows in queries. */
  class Game(_tableTag: Tag) extends profile.api.Table[GameRow](_tableTag, "GAME") {
    def * = (date, homeScore, awayScore, tournament, city, neutral, id, homeTeamId, awayTeamId, shootoutId, countryId).<>(GameRow.tupled, GameRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((date, homeScore, awayScore, tournament, city, neutral, Rep.Some(id), homeTeamId, awayTeamId, shootoutId, countryId)).shaped.<>({r=>import r._; _7.map(_=> GameRow.tupled((_1, _2, _3, _4, _5, _6, _7.get, _8, _9, _10, _11)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column DATE SqlType(DATE) */
    val date: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("DATE")
    /** Database column HOME_SCORE SqlType(INTEGER) */
    val homeScore: Rep[Option[Int]] = column[Option[Int]]("HOME_SCORE")
    /** Database column AWAY_SCORE SqlType(INTEGER) */
    val awayScore: Rep[Option[Int]] = column[Option[Int]]("AWAY_SCORE")
    /** Database column TOURNAMENT SqlType(CHARACTER VARYING), Length(1000000000,true) */
    val tournament: Rep[Option[String]] = column[Option[String]]("TOURNAMENT", O.Length(1000000000,varying=true))
    /** Database column CITY SqlType(CHARACTER VARYING), Length(1000000000,true) */
    val city: Rep[Option[String]] = column[Option[String]]("CITY", O.Length(1000000000,varying=true))
    /** Database column NEUTRAL SqlType(BOOLEAN) */
    val neutral: Rep[Option[Boolean]] = column[Option[Boolean]]("NEUTRAL")
    /** Database column ID SqlType(INTEGER), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column HOME_TEAM_ID SqlType(INTEGER) */
    val homeTeamId: Rep[Option[Int]] = column[Option[Int]]("HOME_TEAM_ID")
    /** Database column AWAY_TEAM_ID SqlType(INTEGER) */
    val awayTeamId: Rep[Option[Int]] = column[Option[Int]]("AWAY_TEAM_ID")
    /** Database column SHOOTOUT_ID SqlType(INTEGER) */
    val shootoutId: Rep[Option[Int]] = column[Option[Int]]("SHOOTOUT_ID")
    /** Database column COUNTRY_ID SqlType(INTEGER) */
    val countryId: Rep[Option[Int]] = column[Option[Int]]("COUNTRY_ID")

    /** Foreign key referencing Country (database name FK_GAME_TO_COUNTRY) */
    lazy val countryFk = foreignKey("FK_GAME_TO_COUNTRY", countryId, Country)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
    /** Foreign key referencing Shootout (database name FK_GAME_TO_SHOOTOUT) */
    lazy val shootoutFk = foreignKey("FK_GAME_TO_SHOOTOUT", shootoutId, Shootout)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
    /** Foreign key referencing Team (database name FK_GAME_TO_TEAM_HOME_AWAY) */
    lazy val teamFk3 = foreignKey("FK_GAME_TO_TEAM_HOME_AWAY", awayTeamId, Team)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
    /** Foreign key referencing Team (database name FK_GAME_TO_TEAM_HOME_TEAM) */
    lazy val teamFk4 = foreignKey("FK_GAME_TO_TEAM_HOME_TEAM", homeTeamId, Team)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
  }
  /** Collection-like TableQuery object for table Game */
  lazy val Game = new TableQuery(tag => new Game(tag))

  /** Entity class storing rows of table Goal
   *  @param player Database column PLAYER SqlType(CHARACTER VARYING), Length(1000000000,true)
   *  @param scoreMinute Database column SCORE_MINUTE SqlType(INTEGER)
   *  @param ownGoal Database column OWN_GOAL SqlType(BOOLEAN)
   *  @param penalty Database column PENALTY SqlType(BOOLEAN)
   *  @param id Database column ID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param gameId Database column GAME_ID SqlType(INTEGER)
   *  @param teamId Database column TEAM_ID SqlType(INTEGER) */
  case class GoalRow(player: Option[String], scoreMinute: Option[Int], ownGoal: Option[Boolean], penalty: Option[Boolean], id: Int, gameId: Option[Int], teamId: Option[Int])
  /** GetResult implicit for fetching GoalRow objects using plain SQL queries */
  implicit def GetResultGoalRow(implicit e0: GR[Option[String]], e1: GR[Option[Int]], e2: GR[Option[Boolean]], e3: GR[Int]): GR[GoalRow] = GR{
    prs => import prs._
    GoalRow.tupled((<<?[String], <<?[Int], <<?[Boolean], <<?[Boolean], <<[Int], <<?[Int], <<?[Int]))
  }
  /** Table description of table GOAL. Objects of this class serve as prototypes for rows in queries. */
  class Goal(_tableTag: Tag) extends profile.api.Table[GoalRow](_tableTag, "GOAL") {
    def * = (player, scoreMinute, ownGoal, penalty, id, gameId, teamId).<>(GoalRow.tupled, GoalRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((player, scoreMinute, ownGoal, penalty, Rep.Some(id), gameId, teamId)).shaped.<>({r=>import r._; _5.map(_=> GoalRow.tupled((_1, _2, _3, _4, _5.get, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column PLAYER SqlType(CHARACTER VARYING), Length(1000000000,true) */
    val player: Rep[Option[String]] = column[Option[String]]("PLAYER", O.Length(1000000000,varying=true))
    /** Database column SCORE_MINUTE SqlType(INTEGER) */
    val scoreMinute: Rep[Option[Int]] = column[Option[Int]]("SCORE_MINUTE")
    /** Database column OWN_GOAL SqlType(BOOLEAN) */
    val ownGoal: Rep[Option[Boolean]] = column[Option[Boolean]]("OWN_GOAL")
    /** Database column PENALTY SqlType(BOOLEAN) */
    val penalty: Rep[Option[Boolean]] = column[Option[Boolean]]("PENALTY")
    /** Database column ID SqlType(INTEGER), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column GAME_ID SqlType(INTEGER) */
    val gameId: Rep[Option[Int]] = column[Option[Int]]("GAME_ID")
    /** Database column TEAM_ID SqlType(INTEGER) */
    val teamId: Rep[Option[Int]] = column[Option[Int]]("TEAM_ID")

    /** Foreign key referencing Game (database name FK_GOAL_TO_GAME) */
    lazy val gameFk = foreignKey("FK_GOAL_TO_GAME", gameId, Game)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
    /** Foreign key referencing Team (database name FK_GOAL_TO_TEAM) */
    lazy val teamFk = foreignKey("FK_GOAL_TO_TEAM", teamId, Team)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
  }
  /** Collection-like TableQuery object for table Goal */
  lazy val Goal = new TableQuery(tag => new Goal(tag))

  /** Entity class storing rows of table Goalscorers
   *  @param date Database column DATE SqlType(DATE)
   *  @param homeTeam Database column HOME_TEAM SqlType(CHARACTER VARYING), Length(255,true)
   *  @param awayTeam Database column AWAY_TEAM SqlType(CHARACTER VARYING), Length(255,true)
   *  @param team Database column TEAM SqlType(CHARACTER VARYING), Length(255,true)
   *  @param scorer Database column SCORER SqlType(CHARACTER VARYING), Length(1000000000,true)
   *  @param minute Database column Minute SqlType(INTEGER)
   *  @param ownGoal Database column OWN_GOAL SqlType(BOOLEAN)
   *  @param penalty Database column PENALTY SqlType(BOOLEAN) */
  case class GoalscorersRow(date: Option[java.sql.Date], homeTeam: Option[String], awayTeam: Option[String], team: Option[String], scorer: Option[String], minute: Option[Int], ownGoal: Option[Boolean], penalty: Option[Boolean])
  /** GetResult implicit for fetching GoalscorersRow objects using plain SQL queries */
  implicit def GetResultGoalscorersRow(implicit e0: GR[Option[java.sql.Date]], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[Boolean]]): GR[GoalscorersRow] = GR{
    prs => import prs._
    GoalscorersRow.tupled((<<?[java.sql.Date], <<?[String], <<?[String], <<?[String], <<?[String], <<?[Int], <<?[Boolean], <<?[Boolean]))
  }
  /** Table description of table GOALSCORERS. Objects of this class serve as prototypes for rows in queries. */
  class Goalscorers(_tableTag: Tag) extends profile.api.Table[GoalscorersRow](_tableTag, "GOALSCORERS") {
    def * = (date, homeTeam, awayTeam, team, scorer, minute, ownGoal, penalty).<>(GoalscorersRow.tupled, GoalscorersRow.unapply)

    /** Database column DATE SqlType(DATE) */
    val date: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("DATE")
    /** Database column HOME_TEAM SqlType(CHARACTER VARYING), Length(255,true) */
    val homeTeam: Rep[Option[String]] = column[Option[String]]("HOME_TEAM", O.Length(255,varying=true))
    /** Database column AWAY_TEAM SqlType(CHARACTER VARYING), Length(255,true) */
    val awayTeam: Rep[Option[String]] = column[Option[String]]("AWAY_TEAM", O.Length(255,varying=true))
    /** Database column TEAM SqlType(CHARACTER VARYING), Length(255,true) */
    val team: Rep[Option[String]] = column[Option[String]]("TEAM", O.Length(255,varying=true))
    /** Database column SCORER SqlType(CHARACTER VARYING), Length(1000000000,true) */
    val scorer: Rep[Option[String]] = column[Option[String]]("SCORER", O.Length(1000000000,varying=true))
    /** Database column Minute SqlType(INTEGER) */
    val minute: Rep[Option[Int]] = column[Option[Int]]("Minute")
    /** Database column OWN_GOAL SqlType(BOOLEAN) */
    val ownGoal: Rep[Option[Boolean]] = column[Option[Boolean]]("OWN_GOAL")
    /** Database column PENALTY SqlType(BOOLEAN) */
    val penalty: Rep[Option[Boolean]] = column[Option[Boolean]]("PENALTY")
  }
  /** Collection-like TableQuery object for table Goalscorers */
  lazy val Goalscorers = new TableQuery(tag => new Goalscorers(tag))

  /** Entity class storing rows of table Results
   *  @param date Database column DATE SqlType(DATE)
   *  @param homeTeam Database column HOME_TEAM SqlType(CHARACTER VARYING), Length(255,true)
   *  @param awayTeam Database column AWAY_TEAM SqlType(CHARACTER VARYING), Length(255,true)
   *  @param homeScore Database column HOME_SCORE SqlType(INTEGER)
   *  @param awayScore Database column AWAY_SCORE SqlType(INTEGER)
   *  @param tournament Database column TOURNAMENT SqlType(CHARACTER VARYING), Length(1000000000,true)
   *  @param city Database column CITY SqlType(CHARACTER VARYING), Length(1000000000,true)
   *  @param country Database column COUNTRY SqlType(CHARACTER VARYING), Length(255,true)
   *  @param neutral Database column NEUTRAL SqlType(BOOLEAN) */
  case class ResultsRow(date: Option[java.sql.Date], homeTeam: Option[String], awayTeam: Option[String], homeScore: Option[Int], awayScore: Option[Int], tournament: Option[String], city: Option[String], country: Option[String], neutral: Option[Boolean])
  /** GetResult implicit for fetching ResultsRow objects using plain SQL queries */
  implicit def GetResultResultsRow(implicit e0: GR[Option[java.sql.Date]], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[Boolean]]): GR[ResultsRow] = GR{
    prs => import prs._
    ResultsRow.tupled((<<?[java.sql.Date], <<?[String], <<?[String], <<?[Int], <<?[Int], <<?[String], <<?[String], <<?[String], <<?[Boolean]))
  }
  /** Table description of table RESULTS. Objects of this class serve as prototypes for rows in queries. */
  class Results(_tableTag: Tag) extends profile.api.Table[ResultsRow](_tableTag, "RESULTS") {
    def * = (date, homeTeam, awayTeam, homeScore, awayScore, tournament, city, country, neutral).<>(ResultsRow.tupled, ResultsRow.unapply)

    /** Database column DATE SqlType(DATE) */
    val date: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("DATE")
    /** Database column HOME_TEAM SqlType(CHARACTER VARYING), Length(255,true) */
    val homeTeam: Rep[Option[String]] = column[Option[String]]("HOME_TEAM", O.Length(255,varying=true))
    /** Database column AWAY_TEAM SqlType(CHARACTER VARYING), Length(255,true) */
    val awayTeam: Rep[Option[String]] = column[Option[String]]("AWAY_TEAM", O.Length(255,varying=true))
    /** Database column HOME_SCORE SqlType(INTEGER) */
    val homeScore: Rep[Option[Int]] = column[Option[Int]]("HOME_SCORE")
    /** Database column AWAY_SCORE SqlType(INTEGER) */
    val awayScore: Rep[Option[Int]] = column[Option[Int]]("AWAY_SCORE")
    /** Database column TOURNAMENT SqlType(CHARACTER VARYING), Length(1000000000,true) */
    val tournament: Rep[Option[String]] = column[Option[String]]("TOURNAMENT", O.Length(1000000000,varying=true))
    /** Database column CITY SqlType(CHARACTER VARYING), Length(1000000000,true) */
    val city: Rep[Option[String]] = column[Option[String]]("CITY", O.Length(1000000000,varying=true))
    /** Database column COUNTRY SqlType(CHARACTER VARYING), Length(255,true) */
    val country: Rep[Option[String]] = column[Option[String]]("COUNTRY", O.Length(255,varying=true))
    /** Database column NEUTRAL SqlType(BOOLEAN) */
    val neutral: Rep[Option[Boolean]] = column[Option[Boolean]]("NEUTRAL")
  }
  /** Collection-like TableQuery object for table Results */
  lazy val Results = new TableQuery(tag => new Results(tag))

  /** Entity class storing rows of table Shootout
   *  @param id Database column ID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param winnerId Database column WINNER_ID SqlType(INTEGER) */
  case class ShootoutRow(id: Int, winnerId: Option[Int])
  /** GetResult implicit for fetching ShootoutRow objects using plain SQL queries */
  implicit def GetResultShootoutRow(implicit e0: GR[Int], e1: GR[Option[Int]]): GR[ShootoutRow] = GR{
    prs => import prs._
    ShootoutRow.tupled((<<[Int], <<?[Int]))
  }
  /** Table description of table SHOOTOUT. Objects of this class serve as prototypes for rows in queries. */
  class Shootout(_tableTag: Tag) extends profile.api.Table[ShootoutRow](_tableTag, "SHOOTOUT") {
    def * = (id, winnerId).<>(ShootoutRow.tupled, ShootoutRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), winnerId)).shaped.<>({r=>import r._; _1.map(_=> ShootoutRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INTEGER), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column WINNER_ID SqlType(INTEGER) */
    val winnerId: Rep[Option[Int]] = column[Option[Int]]("WINNER_ID")

    /** Foreign key referencing Team (database name FK_SHOOTOUT_TO_TEAM) */
    lazy val teamFk = foreignKey("FK_SHOOTOUT_TO_TEAM", winnerId, Team)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
  }
  /** Collection-like TableQuery object for table Shootout */
  lazy val Shootout = new TableQuery(tag => new Shootout(tag))

  /** Entity class storing rows of table Shootouts
   *  @param date Database column DATE SqlType(DATE)
   *  @param homeTeam Database column HOME_TEAM SqlType(CHARACTER VARYING), Length(255,true)
   *  @param awayTeam Database column AWAY_TEAM SqlType(CHARACTER VARYING), Length(255,true)
   *  @param winner Database column WINNER SqlType(CHARACTER VARYING), Length(255,true) */
  case class ShootoutsRow(date: Option[java.sql.Date], homeTeam: Option[String], awayTeam: Option[String], winner: Option[String])
  /** GetResult implicit for fetching ShootoutsRow objects using plain SQL queries */
  implicit def GetResultShootoutsRow(implicit e0: GR[Option[java.sql.Date]], e1: GR[Option[String]]): GR[ShootoutsRow] = GR{
    prs => import prs._
    ShootoutsRow.tupled((<<?[java.sql.Date], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table SHOOTOUTS. Objects of this class serve as prototypes for rows in queries. */
  class Shootouts(_tableTag: Tag) extends profile.api.Table[ShootoutsRow](_tableTag, "SHOOTOUTS") {
    def * = (date, homeTeam, awayTeam, winner).<>(ShootoutsRow.tupled, ShootoutsRow.unapply)

    /** Database column DATE SqlType(DATE) */
    val date: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("DATE")
    /** Database column HOME_TEAM SqlType(CHARACTER VARYING), Length(255,true) */
    val homeTeam: Rep[Option[String]] = column[Option[String]]("HOME_TEAM", O.Length(255,varying=true))
    /** Database column AWAY_TEAM SqlType(CHARACTER VARYING), Length(255,true) */
    val awayTeam: Rep[Option[String]] = column[Option[String]]("AWAY_TEAM", O.Length(255,varying=true))
    /** Database column WINNER SqlType(CHARACTER VARYING), Length(255,true) */
    val winner: Rep[Option[String]] = column[Option[String]]("WINNER", O.Length(255,varying=true))
  }
  /** Collection-like TableQuery object for table Shootouts */
  lazy val Shootouts = new TableQuery(tag => new Shootouts(tag))

  /** Entity class storing rows of table Team
   *  @param id Database column ID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param teamName Database column TEAM_NAME SqlType(CHARACTER VARYING), Length(255,true) */
  case class TeamRow(id: Int, teamName: Option[String])
  /** GetResult implicit for fetching TeamRow objects using plain SQL queries */
  implicit def GetResultTeamRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[TeamRow] = GR{
    prs => import prs._
    TeamRow.tupled((<<[Int], <<?[String]))
  }
  /** Table description of table TEAM. Objects of this class serve as prototypes for rows in queries. */
  class Team(_tableTag: Tag) extends profile.api.Table[TeamRow](_tableTag, "TEAM") {
    def * = (id, teamName).<>(TeamRow.tupled, TeamRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), teamName)).shaped.<>({r=>import r._; _1.map(_=> TeamRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INTEGER), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column TEAM_NAME SqlType(CHARACTER VARYING), Length(255,true) */
    val teamName: Rep[Option[String]] = column[Option[String]]("TEAM_NAME", O.Length(255,varying=true))
  }
  /** Collection-like TableQuery object for table Team */
  lazy val Team = new TableQuery(tag => new Team(tag))
}
