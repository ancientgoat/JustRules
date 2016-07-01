name := "JustRules"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  "org.springframework" % "spring-jdbc" % "4.2.5.RELEASE",
  "org.springframework" % "spring-orm" % "4.2.5.RELEASE",
  "org.springframework" % "spring-tx" % "4.2.5.RELEASE",
  "org.springframework" % "spring-expression" % "4.2.5.RELEASE",
  "org.springframework" % "spring-context" % "4.2.5.RELEASE",
  "org.springframework" % "spring-test" % "4.2.5.RELEASE",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final",
  "mysql" % "mysql-connector-java" % "5.1.35",
  "javax.inject" % "javax.inject" % "1",
  "org.webjars" % "bootstrap" % "2.1.1",
  "commons-io" % "commons-io" % "2.5",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.7.3",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.7.3",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.7.3",
  // Test
  "com.h2database" % "h2" % "1.4.181" % "test",
  "org.mockito" % "mockito-all" % "1.10.19" % "test"
)
