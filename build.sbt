ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "InfoSysteme"
  )

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "2.2.224",
  "com.typesafe.slick" %% "slick" % "3.4.1",
  "org.slf4j" % "slf4j-nop" % "2.0.5",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",
  "com.typesafe.slick" %% "slick-codegen" % "3.4.1",
  // Aufgabe 2
  "redis.clients" % "jedis" % "4.4.0"
  //Aufgabe 3
)