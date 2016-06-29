name := """BetPlace"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  "org.springframework" % "spring-jdbc" % "4.2.5.RELEASE",
  "org.springframework" % "spring-orm" % "4.2.5.RELEASE",
  "org.springframework" % "spring-tx" % "4.2.5.RELEASE",
  "org.springframework" % "spring-aop" % "4.2.5.RELEASE",
  "org.springframework" % "spring-expression" % "4.2.5.RELEASE",
  "org.springframework" % "spring-test" % "4.2.5.RELEASE",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final",
  "mysql" % "mysql-connector-java" % "5.1.35",
  "javax.inject" % "javax.inject" % "1",
  "org.webjars" % "bootstrap" % "2.1.1",
  "org.webjars" % "jquery" % "1.11.2",
  "com.h2database" % "h2" % "1.4.181" % "test",
  "commons-io" % "commons-io" % "2.5"
)
