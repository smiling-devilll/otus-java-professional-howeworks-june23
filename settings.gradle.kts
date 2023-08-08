rootProject.name = "otus-java-professional-howeworks-june23"

include("hw01-gradle")

pluginManagement {

    val johnrengelmanShadow: String by settings
    val dependencyManagement: String by settings

    plugins {
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("io.spring.dependency-management") version dependencyManagement
    }
}
