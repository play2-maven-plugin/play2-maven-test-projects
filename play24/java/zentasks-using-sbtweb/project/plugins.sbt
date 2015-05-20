// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "https://dl.bintray.com/typesafe/maven-releases/"

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.0.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-coffeescript" % "1.0.0")
