name := "Logalyzer"

version := "0.1.1"

scalaVersion := "2.13.6"
val hadoopVersion = "1.2.1"

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-core" % hadoopVersion
)