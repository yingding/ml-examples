name := """server"""
organization := "com.example"
version := "1.0.0"

// Warning:
// the name and the lazy val `server` shall be the same otherwise play2 app config can not launch the play app
// module id in guice
lazy val `server` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.12" // spark 3.0.1 binary is compile with 2.12.10
// scalaVersion := "2.13.2"
// play jongo, spark doesn't work with 2.13.x jet, they are still compiled with 2.12.x

libraryDependencies += "com.typesafe.play" %% "play" % "2.8.2" // "2.7.3"
// the Json.toJson uses play-json
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.1" // "2.7.4"
// libraryDependencies += "com.typesafe.play" %% "play-iteratees" % "2.6.1"
// libraryDependencies += javaJdbc
// libraryDependencies += cacheApi

// allow calling http service from play frame work
// Enable Play default provided modules
libraryDependencies ++= Seq(
  guice,
  javaWs
  // ,ehcache
)

// https://mvnrepository.com/artifact/org.jongo/jongo
// libraryDependencies += "org.jongo" % "jongo" % "1.4.1"

// for the mongo db 3.4.x version,// "org.jongo" % "jongo" % "1.3.1",
libraryDependencies ++= Seq(
  "org.mongodb" % "mongo-java-driver" % "3.6.4",
  "org.jongo" % "jongo" % "1.4.1",
  "uk.co.panaxiom" %% "play-jongo" % "2.1.0-jongo1.3"
)

// for morphia with play 2.8.x
//libraryDependencies ++= Seq(
//  "org.mongodb" % "mongo-java-driver" % "3.12.0",
//  "dev.morphia.morphia" % "core" % "1.5.8"
//)

// libraryDependencies += "commons-collections" % "commons-collections" % "3.2.1"

/* spark mllib scala 2.12.x, Overrides also Overrides the play jongo */
//dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.9.10"
//dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.10"
//dependencyOverrides += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.10"
//libraryDependencies ++= Seq(
//  "org.apache.spark" %% "spark-core" % "2.4.7",
//  "org.apache.spark" %% "spark-sql" % "2.4.7",
//  "org.apache.spark" %% "spark-mllib" % "2.4.7" % "runtime",
//  "org.apache.spark" %% "spark-streaming" % "2.4.7" % "provided"
//)

/* spark mllib scala 2.12.x, %% are the modules with dependency on different scala versions 2.11.2 */
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.11.2"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.11.2"
dependencyOverrides += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.11.2"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.0.1",
  "org.apache.spark" %% "spark-sql" % "3.0.1",
  "org.apache.spark" %% "spark-mllib" % "3.0.1" % "runtime",
  "org.apache.spark" %% "spark-streaming" % "3.0.1" % "provided",
  "org.mongodb.spark" %% "mongo-spark-connector" % "3.0.0" // mongo spark connect 3 for spark 3.0.x to load mongo with spark with scala 2.12
)

// Issue with the Logging
// https://www.playframework.com/documentation/2.8.x/SettingsLogger

// https://stackoverflow.com/questions/29647669/multiple-slf4j-bindings-with-play-2-3-8

/* end of mllib */
// https://discuss.lightbend.com/t/play-framework-apache-spark/2908/4
