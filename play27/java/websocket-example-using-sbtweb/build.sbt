scalaVersion := Option(System.getProperty("scala.version")).getOrElse("2.12.8")

lazy val main = (project in file("."))
  .enablePlugins(SbtWeb)
  .settings(
    libraryDependencies ++= Seq(
      "org.webjars" % "bootstrap" % "2.3.2",
      "org.webjars" % "flot" % "0.8.3"
    ),
    sourceDirectory in Assets := baseDirectory.value / "app/assets",
    resourceDirectory in Assets := baseDirectory.value / "public",
    LessKeys.compress := true,
    target := baseDirectory.value / "target/sbt"
  )
