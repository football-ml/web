name := "footballml"

version := "1.0"

lazy val `footballml` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "org.apache.spark" %% "spark-core" % "2.0.0" % "compile",
  "org.apache.spark" %% "spark-sql" % "2.0.0" % "compile",
  "org.apache.spark" %% "spark-mllib" % "2.0.0" % "compile",
  "org.apache.spark" %% "spark-hive" % "2.0.0" % "compile",
  "com.typesafe.play" %% "play" % "2.5.4" % "compile" excludeAll(
    ExclusionRule(organization = "com.fasterxml.jackson.core"),
    ExclusionRule(organization = "ch.qos.logback")
    ),
  "com.typesafe.akka" %% "akka-actor" % "2.4.9-RC1",
  "com.typesafe.akka" %% "akka-agent" % "2.4.9-RC1"
)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  