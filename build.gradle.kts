plugins {
    id("io.spring.dependency-management")
}


allprojects {
    group = "ru.smiling_devilll"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    val guava: String by project

    apply(plugin = "io.spring.dependency-management")
    dependencyManagement {
        dependencies {
            dependency("com.google.guava:guava:$guava")
        }
    }
}

subprojects {
    plugins.apply(JavaPlugin::class.java)

    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-Xlint:all,-serial,-processing"))
    }

}