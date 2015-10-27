name := "Adbrain Weather"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.5.1"
libraryDependencies += "joda-time" % "joda-time" % "2.8.2"
libraryDependencies += "org.joda" % "joda-convert" % "1.8"
libraryDependencies += "org.joda" % "joda-convert" % "1.8"
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "1.5.0-M2"
mainClass in Compile := Some("AdWeather")

mainClass in assembly := some("AdWeather")
assemblyJarName := "AdWeather.jar"

val meta = """META.INF(.)*""".r
assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case n if n.startsWith("reference.conf") => MergeStrategy.concat
  case n if n.endsWith(".conf") => MergeStrategy.concat
  case meta(_) => MergeStrategy.discard
  case x => MergeStrategy.first
}
