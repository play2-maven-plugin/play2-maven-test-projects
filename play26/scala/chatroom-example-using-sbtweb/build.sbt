scalaVersion := Option(System.getProperty("scala.version")).getOrElse("2.12.1")

lazy val main = (project in file("."))
  .enablePlugins(SbtWeb)
  .settings(
    libraryDependencies ++= Seq(
      "org.webjars" % "bootstrap" % "3.3.6",
      "org.webjars" % "flot" % "0.8.3"
    ),
    sourceDirectory in Assets := baseDirectory.value / "app/assets",
    resourceDirectory in Assets := baseDirectory.value / "public",
    target := baseDirectory.value / "target/sbt"
  )
