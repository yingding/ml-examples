name := """server"""
organization := "com.example"
version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"
// scalaVersion := "2.13.2"
// play jongo doesn't work with 2.13.x

libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play" % "2.8.2" // "2.7.3"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.1" // "2.7.4"
// libraryDependencies += "com.typesafe.play" %% "play-iteratees" % "2.6.1"
// libraryDependencies += javaJdbc
// libraryDependencies += cacheApi

// allow calling http service from play frame work
libraryDependencies += javaWs

// for the mongo db 3.4.x version,
//libraryDependencies ++= Seq(
//  "org.mongodb" % "mongo-java-driver" % "3.6.4",
//  "org.jongo" % "jongo" % "1.3.1",
//  "uk.co.panaxiom" %% "play-jongo" % "2.1.0-jongo1.3"
//)

// for morphia with play 2.8.x
libraryDependencies ++= Seq(
  "org.mongodb" % "mongo-java-driver" % "3.12.0",
  "dev.morphia.morphia" % "core" % "1.5.8"
)

libraryDependencies += "commons-collections" % "commons-collections" % "3.2.1"

/* spark mllib */
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.9.10"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.10"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.9.10"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.7",
  "org.apache.spark" %% "spark-sql" % "2.4.7",
  "org.apache.spark" %% "spark-mllib" % "2.4.7" % "runtime",
  "org.apache.spark" %% "spark-streaming" % "2.4.7" % "provided"
)
/* end of mllib */

// https://discuss.lightbend.com/t/play-framework-apache-spark/2908/4
