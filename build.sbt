import sbt.Keys._

lazy val root = (project in file(".")).
  enablePlugins(TomcatPlugin).
  settings(
    name := "places",
    version := "1.0",
    scalaVersion := "2.11.8",
    sbtVersion := "0.13.13"
  )

val vTomcat: String = "8.0.30"
val vScalaTest: String = "3.0.0"
val vAkka: String = "2.4.10"
val vSpringBoot: String = "1.4.2.RELEASE"

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

  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "org.webjars" % "bootstrap" % "3.1.1",
  "org.thymeleaf" % "thymeleaf-spring4" % "2.1.2.RELEASE",
  "nz.net.ultraq.thymeleaf" % "thymeleaf-layout-dialect" % "1.2.1",
  "org.hsqldb" % "hsqldb" % "2.3.1",
  "org.hibernate" % "hibernate-validator" % "5.2.2.Final",
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided",

  "org.apache.tomcat.embed" % "tomcat-embed-core" % vTomcat % "container",
  "org.apache.tomcat.embed" % "tomcat-embed-el" % vTomcat % "container",
  "org.apache.tomcat.embed" % "tomcat-embed-logging-juli" % vTomcat % "container",
  "org.apache.tomcat.embed" % "tomcat-embed-jasper" % vTomcat % "container"
)

