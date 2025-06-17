import Dependencies._

ThisBuild / scalaVersion     := "3.6.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "registration",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest-flatspec" % "3.2.19" % "test",
      "org.scalatestplus" %% "mockito-5-18" % "3.2.19.0" % "test"
    )
  )

