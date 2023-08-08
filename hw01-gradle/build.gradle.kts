import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id ("com.github.johnrengelman.shadow")
}

dependencies {
    api("com.google.guava:guava")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("gradleHelloWorld")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.smiling_devilll.hw01.HelloOtus"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}
