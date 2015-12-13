// Comment to get more information during initialization
logLevel := Level.Warn

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"

// The Typesafe repository
resolvers += Resolver.typesafeRepo("releases")

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.2.2")
