lazy val root = (project in file("."))
  .settings(
    organization := "$organizationName$",
    name := "$projectName$",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.12.8",
    crossScalaVersions := Seq("2.12.8", "2.11.12"),
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-Xfuture"),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    ),
    // FYI: https://www.scala-sbt.org/1.0/docs/Using-Sonatype.html
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (version.value.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at s"\${nexus}content/repositories/snapshots")
      else Some("releases" at s"\${nexus}service/local/staging/deploy/maven2")
    },
    pomExtra := {
      <url>https://github.com/$githubAccountName$/$projectName$</url>
      <licenses>
        <license>
          <name>Apache License 2.0</name>
          <url>https://raw.githubusercontent.com/$githubAccountName$/$projectName$/master/LICENSE.txt</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:$githubAccountName$/$projectName$.git</url>
        <connection>scm:git:git@github.com:$githubAccountName$/$projectName$.git</connection>
      </scm>
      <developers>
        <developer>
          <id>$githubAccountName$</id>
          <name>$yourFullName$</name>
          <url>https://github.com/$githubAccountName$</url>
        </developer>
      </developers>
    }
  )
  .settings(mimaSettings)

val mimaSettings = MimaPlugin.mimaDefaultSettings ++ Seq(
  mimaPreviousArtifacts := {
    val previousVersions: Set[String] = Set.empty // e.g. Set("0.1.0", "0.1.1")
    previousVersions.map { v =>
      organization.value % s"\${name.value}_\${scalaBinaryVersion.value}" % v
    }
  },
  test in Test := {
    mimaReportBinaryIssues.value
    (test in Test).value
  }
)
