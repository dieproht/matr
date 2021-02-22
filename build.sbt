val _scalaVersions: Seq[String] = Seq("3.0.0-M3")

def commonSettings = Seq(
  ThisBuild / organization := "matr",
  ThisBuild / version := "0.1.0-SNAPSHOT",
  ThisBuild / scalaVersion := _scalaVersions.last,
  ThisBuild / publishMavenStyle := true,
  licenses += ("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0")),
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
    "3.1"
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

lazy val matr_tests =
  crossProject(JVMPlatform)
    .crossType(CrossType.Full)
    .withoutSuffixFor(JVMPlatform)
    .in(file("matr-tests"))
    .settings(commonSettings: _*)
    .settings(
      name := "matr-tests",
      libraryDependencies ++= Seq(
        "org.scalatest"     %% "scalatest"       % "3.2.3"   % Test,
        "org.scalatestplus" %% "scalacheck-1-15" % "3.2.3.0" % Test
      )
    )
    .dependsOn(matr_api, matr_dflt_data, matr_dflt_ops, matr_std)

lazy val matr =
  crossProject(JVMPlatform)
    .in(file("."))
    .aggregate(matr_api, matr_dflt_data, matr_dflt_ops, matr_std, matr_tests)
