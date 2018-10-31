import sbt.Keys._

lazy val root = (project in file(".")).
  enablePlugins(TomcatPlugin).
  settings(
    name := "myScalaTutorial",
    version := "1.0",
    scalaVersion := "2.12.7",
    sbtVersion := "1.2.6"
  )

val vTomcat: String = "8.5.2"
val vScalaTest: String = "3.0.5"
val vAkka: String = "2.5.17"
val vSpringBoot: String = "2.0.6.RELEASE"
val vJackson: String = "2.9.7"

libraryDependencies ++= Seq(
  //UNIT-TESTING
  "org.scalactic" %% "scalactic" % vScalaTest, //http://www.scalactic.org/
  "org.scalatest" %% "scalatest" % vScalaTest % "test",

  //AKKA, parallel actors
  "com.typesafe.akka" %% "akka-actor" % vAkka,
  "com.typesafe.akka" %% "akka-testkit" % vAkka % "test",

  //SPRING-BOOT, https://github.com/spring-projects/spring-boot/tree/master/spring-boot-starters
  "org.springframework.boot" % "spring-boot-starter-web" % vSpringBoot,
  "org.springframework.boot" % "spring-boot-starter-data-jpa" % vSpringBoot,
  "org.springframework.boot" % "spring-boot-starter-test" % vSpringBoot % "test",
  "org.springframework.boot" % "spring-boot-starter-tomcat" % vSpringBoot % "provided",

  //VARIOUS ADDITIONS FOR SPRING-BOOT APP
  "org.hsqldb" % "hsqldb" % "2.3.1",
  "org.hibernate" % "hibernate-validator" % "5.2.2.Final",
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided",
  "org.webjars" % "bootstrap" % "3.1.1",

  //COMMON UTILS
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "org.assertj" % "assertj-core" % "3.5.2",

  //JACKSON FOR YAML
  "com.fasterxml.jackson.core" % "jackson-core" % vJackson,
  "com.fasterxml.jackson.core" % "jackson-annotations" % vJackson,
  "com.fasterxml.jackson.core" % "jackson-databind" % vJackson,
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % vJackson,

  //EMBEDDED TOMCAT
  "org.apache.tomcat.embed" % "tomcat-embed-core" % vTomcat % "container",
  "org.apache.tomcat.embed" % "tomcat-embed-el" % vTomcat % "container",
  "org.apache.tomcat.embed" % "tomcat-embed-logging-juli" % vTomcat % "container",
  "org.apache.tomcat.embed" % "tomcat-embed-jasper" % vTomcat % "container"
)

