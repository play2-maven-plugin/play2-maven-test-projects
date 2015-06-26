scalaVersion := Option(System.getProperty("scala.version")).getOrElse("2.11.6")

lazy val main = (project in file("."))
  .enablePlugins(SbtWeb)
  .settings(
    sourceDirectory in Assets := baseDirectory.value / "app/assets",
    resourceDirectory in Assets := baseDirectory.value / "public",
    target := baseDirectory.value / "target/sbt",
    libraryDependencies ++= Seq(
      "org.webjars" % "bootstrap" % "2.3.2",
      "org.webjars" % "flot" % "0.8.0"
    )
  )
