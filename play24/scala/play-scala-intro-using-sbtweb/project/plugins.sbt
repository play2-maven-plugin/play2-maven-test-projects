// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "https://dl.bintray.com/typesafe/maven-releases/"

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.2.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-coffeescript" % "1.0.0")
