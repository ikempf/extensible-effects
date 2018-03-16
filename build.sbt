import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.ikempf",
      scalaVersion := "2.12.4",
      version := "0.1.0-SNAPSHOT",
      addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")
    )),
    scalacOptions += "-Ypartial-unification",
    name := "extensible-effects",
    libraryDependencies += eff
//    libraryDependencies += cats
  )
