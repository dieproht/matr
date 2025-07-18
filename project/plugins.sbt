resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("org.portable-scala" % "sbt-crossproject" % "1.3.2")
addSbtPlugin("com.github.sbt"     % "sbt-ci-release"   % "1.11.1")
addSbtPlugin("org.scalameta"      % "sbt-scalafmt"     % "2.5.5")
addSbtPlugin("ch.epfl.scala"      % "sbt-scalafix"     % "0.14.3")
