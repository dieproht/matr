resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("org.portable-scala" % "sbt-crossproject" % "1.3.1")
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.9.2")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.4")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.11.1")
