name := "places"
version := "1.0"
scalaVersion := "2.11.8"

def scalactic = "org.scalactic" %% "scalactic" % "3.0.0"
def scalatest = "org.scalatest" %% "scalatest" % "3.0.0" % "test"
def akka = "com.typesafe.akka" %% "akka-actor" % "2.4.10"
def akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % "2.4.10" % "test"

libraryDependencies += scalactic
libraryDependencies += scalatest
libraryDependencies += akka
libraryDependencies += akkaTestkit