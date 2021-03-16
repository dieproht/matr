resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("org.portable-scala" % "sbt-crossproject" % "1.0.0")
addSbtPlugin("ch.epfl.lamp"       % "sbt-dotty"        % "0.5.3")
addSbtPlugin("com.geirsson"       % "sbt-ci-release"   % "1.5.6")
addSbtPlugin("org.xerial.sbt"     % "sbt-sonatype"     % "3.9.7")
