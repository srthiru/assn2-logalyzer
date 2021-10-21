name := "Logalyzer"

version := "0.1.1"

scalaVersion := "3.0.2"
val hadoopVersion = "1.2.1"
val sfl4jVersion = "2.0.0-alpha5"
val scalacticVersion = "3.2.9"
val typesafeConfigVersion = "1.4.1"

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-core" % hadoopVersion,
  "org.slf4j" % "slf4j-api" % sfl4jVersion,
  "org.scalatest" %% "scalatest" % scalacticVersion % Test,
  "org.scalactic" %% "scalactic" % scalacticVersion,
  "com.typesafe" % "config" % typesafeConfigVersion
)