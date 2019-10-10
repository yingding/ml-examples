name := """server"""

version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"
// scalaVersion := "2.13.0"
// play jongo doesn't work with 2.13.x

libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play" % "2.7.3"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.7.4"
// libraryDependencies += "com.typesafe.play" %% "play-iteratees" % "2.6.1"
// libraryDependencies += javaJdbc
// libraryDependencies += cacheApi

// allow calling http service from play frame work
libraryDependencies += javaWs

// for the mongo db 3.4.x version
libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.6.4"
libraryDependencies += "org.jongo" % "jongo" % "1.3.1"
libraryDependencies += "uk.co.panaxiom" %% "play-jongo" % "2.1.0-jongo1.3"

libraryDependencies += "commons-collections" % "commons-collections" % "3.2.1"
