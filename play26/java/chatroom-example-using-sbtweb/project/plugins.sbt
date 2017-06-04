// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += Resolver.typesafeRepo("releases")

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.4.1")

// Required by org.webjars:webjars-locator-core
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.7"
