rootProject.name = "otus-java-professional-howeworks-june23"

include("hw01-gradle")
include("hw02-generics")
include("hw03-test-framework")
include("hw05-aop")
include("hw07-patterns")
include("hw08-io")
include("hw09-orm")

pluginManagement {

    val johnrengelmanShadow: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings

    plugins {
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot

    }
}
