package ExerciseOne

object generateTables {
  val profile = "slick.jdbc.H2Profile"
  val jdbcDriver = "org.h2.Driver"
  val outputFolder = "C:\\Users\\janin\\IdeaProjects\\InfoSysteme\\src\\main\\scala\\ExerciseOne"
  val pkg = "footballTables"
  val user = "sa"
  val password = "1234"
  val url = "jdbc:h2:/localhost/~/football"

  def autoGenerate(args: Array[String]): Unit = {
    slick.codegen.SourceCodeGenerator.main(
      Array(profile, jdbcDriver, url, outputFolder, pkg, user, password)
    )
  }
}