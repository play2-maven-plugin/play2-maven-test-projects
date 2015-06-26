scalaVersion := Option(System.getProperty("scala.version")).getOrElse("2.11.6")

lazy val main = (project in file("."))
  .enablePlugins(SbtWeb)
  .settings(
    sourceDirectory in Assets := baseDirectory.value / "app/assets",
    resourceDirectory in Assets := baseDirectory.value / "public",
    target := baseDirectory.value / "target/sbt",
    libraryDependencies ++= Seq(
      "org.webjars" % "angularjs" % "1.3.0",
      "org.webjars" % "requirejs" % "2.1.11-1"
    ),
    pipelineStages := Seq(rjs, digest, gzip)
  )
