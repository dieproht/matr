resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("org.portable-scala" % "sbt-crossproject" % "1.2.0")
addSbtPlugin("com.geirsson" % "sbt-ci-release" % "1.5.7")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.0")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.11.0")
