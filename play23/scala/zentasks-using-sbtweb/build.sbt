scalaVersion := Option(System.getProperty("scala.version")).getOrElse("2.11.11")

lazy val main = (project in file("."))
  .enablePlugins(SbtWeb)
  .settings(
    sourceDirectory in Assets := baseDirectory.value / "app/assets",
    resourceDirectory in Assets := baseDirectory.value / "public",
    target := baseDirectory.value / "target/sbt",
    includeFilter in (Assets, LessKeys.less) := "*.less",
    excludeFilter in (Assets, LessKeys.less) := "_*.less"
  )
