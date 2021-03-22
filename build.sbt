val _scalaVersions: Seq[String] = Seq("3.0.0-RC1")

ThisBuild / organization := "io.github.dieproht"
ThisBuild / homepage := Some(url("https://github.com/dieproht/matr"))
ThisBuild / scalaVersion := _scalaVersions.last
ThisBuild / licenses += ("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0"))
ThisBuild / developers := List(
  Developer(
    "dieproht",
    "Karl F Walkow",
    "opensource@walkow.de",
    url("https://github.com/dieproht")
  )
)
sonatypeCredentialHost := "s01.oss.sonatype.org"
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"

def commonSettings = Seq(
  sonatypeCredentialHost := "s01.oss.sonatype.org",
  sonatypeRepository := "https://s01.oss.sonatype.org/service/local",
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-explain",
    "-explain-types",
    "-feature",
    "-new-syntax",
    "-unchecked"
  )
)

def strictCommonSettings = commonSettings ++ Seq(
  scalacOptions ++= Seq(
    "-source",
    "future"
  )
)

lazy val matr_api =
  crossProject(JVMPlatform)
    .crossType(CrossType.Full)
    .withoutSuffixFor(JVMPlatform)
    .in(file("matr-api"))
    .settings(strictCommonSettings: _*)
    .settings(
      name := "matr-api"
    )

lazy val matr_dflt_data =
  crossProject(JVMPlatform)
    .crossType(CrossType.Full)
    .withoutSuffixFor(JVMPlatform)
    .in(file("matr-dflt-data"))
    .settings(strictCommonSettings: _*)
    .settings(
      name := "matr-dflt-data"
    )
    .dependsOn(matr_api)

lazy val matr_dflt_ops =
  crossProject(JVMPlatform)
    .crossType(CrossType.Full)
    .withoutSuffixFor(JVMPlatform)
    .in(file("matr-dflt-ops"))
    .settings(strictCommonSettings: _*)
    .settings(
      name := "matr-dflt-ops"
    )
    .dependsOn(matr_api)

lazy val matr_std =
  crossProject(JVMPlatform)
    .crossType(CrossType.Full)
    .withoutSuffixFor(JVMPlatform)
    .in(file("matr-std"))
    .settings(strictCommonSettings: _*)
    .settings(
      name := "matr-std"
    )
    .dependsOn(matr_api)

lazy val matr_bundle =
  crossProject(JVMPlatform)
    .crossType(CrossType.Full)
    .withoutSuffixFor(JVMPlatform)
    .in(file("matr-bundle"))
    .settings(strictCommonSettings: _*)
    .settings(
      name := "matr-bundle"
    )
    .dependsOn(matr_dflt_data, matr_dflt_ops, matr_std)

lazy val matr_tests =
  crossProject(JVMPlatform)
    .crossType(CrossType.Full)
    .withoutSuffixFor(JVMPlatform)
    .in(file("matr-tests"))
    .settings(commonSettings: _*)
    .settings(
      name := "matr-tests",
      libraryDependencies ++= Seq(
        "org.scalatest"     %% "scalatest"       % "3.2.6"   % Test,
        "org.scalatestplus" %% "scalacheck-1-15" % "3.2.6.0" % Test
      ),
      publish / skip := true
    )
    .dependsOn(matr_bundle)

lazy val matrJVM =
  project
    .in(file("."))
    .settings(publish / skip := true)
    .aggregate(matr_api.jvm, matr_dflt_data.jvm, matr_dflt_ops.jvm, matr_std.jvm, matr_bundle.jvm, matr_tests.jvm)
