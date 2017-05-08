scalaVersion := Option(System.getProperty("scala.version")).getOrElse("2.11.11")

val securesocialVersion = Option(System.getProperty("securesocial.version")).getOrElse("3.0-M4")

lazy val main = (project in file("."))
  .enablePlugins(SbtWeb)
  .settings(
    libraryDependencies += "ws.securesocial" %% "securesocial" % securesocialVersion intransitive(),
    sourceDirectory in Assets := baseDirectory.value / "app/assets",
    resourceDirectory in Assets := baseDirectory.value / "public",
    target := baseDirectory.value / "target/sbt"
  )
