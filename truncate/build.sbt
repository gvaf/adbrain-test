lazy val root = (project in file(".")).
  settings(
    name := "truncate",
    version := "1.0",
    scalaVersion := "2.10.4"
  )

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.2.4" % "test"
