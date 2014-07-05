import sbt._
import Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._

import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._

object Build extends sbt.Build{
  def qq(v: String) =
    if (v startsWith "2.11.") Nil
    else Seq("org.scalamacros" %% s"quasiquotes" % "2.0.0")
  val cross = new utest.jsrunner.JsCrossBuild(
    organization := "com.lihaoyi",

    version := "0.1.0-SNAPSHOT",
    name := "autowire",
    scalaVersion := "2.11.0",
    libraryDependencies ++= Seq(
      compilerPlugin("org.scalamacros" % s"paradise" % "2.0.0" cross CrossVersion.full),
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "com.chuusai" %% "shapeless" % "2.0.0" % "test"

    ) ++ qq(scalaVersion.value),
    // Sonatype
    publishArtifact in Test := false,
    publishTo <<= version { (v: String) =>
      Some("releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
    },

    pomExtra :=
      <url>https://github.com/lihaoyi/ajax</url>
      <licenses>
        <license>
          <name>MIT license</name>
          <url>http://www.opensource.org/licenses/mit-license.php</url>
        </license>
      </licenses>
      <scm>
        <url>git://github.com/lihaoyi/ajax.git</url>
        <connection>scm:git://github.com/lihaoyi/ajax.git</connection>
      </scm>
      <developers>
        <developer>
          <id>lihaoyi</id>
          <name>Li Haoyi</name>
          <url>https://github.com/lihaoyi</url>
        </developer>
      </developers>
  )

  lazy val root = cross.root

  lazy val js = cross.js.settings(
    libraryDependencies += "com.lihaoyi" %%% "upickle" % "0.1.3"
  )

  lazy val jvm = cross.jvm.settings(
    libraryDependencies += "com.lihaoyi" %%% "upickle" % "0.1.3"
  )
}
