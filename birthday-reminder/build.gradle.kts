import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES

plugins {
    id("java")
    id("io.spring.dependency-management")
    id("org.springframework.boot")
}

group = "ru.smiling.devilll.birthday.reminder"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}
plugins.apply(JavaPlugin::class.java)

extensions.configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:all,-serial,-processing"))
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging.showExceptions = true
    reports {
        junitXml.required.set(true)
        html.required.set(true)
    }
}

dependencies {
    val guava: String by project
    val testcontainersBom: String by project

    val reflections: String by project

    val bootstrap: String by project
    val springDocOpenapiUi: String by project
    val jsr305: String by project
    val grpc: String by project

    apply(plugin = "io.spring.dependency-management")
    dependencyManagement {
        dependencies {
            imports {
                mavenBom(BOM_COORDINATES)
            }
            annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
            implementation("org.springframework.boot:spring-boot-starter-validation")
            implementation("org.springframework:spring-orm")
            implementation("com.google.guava:guava:$guava")

            implementation("org.postgresql:postgresql")
//            implementation("org.apache.logging.log4j:log4j-api")
//            implementation("org.apache.logging.log4j:log4j-core")
//            implementation("org.apache.logging.log4j:log4j-slf4j-impl")

            implementation("org.hibernate.orm:hibernate-core")

            implementation("org.telegram:telegrambots:6.9.7.0"){
                exclude(group = "commons-logging", module = "commons-logging")
            }
            implementation("org.telegram:telegrambots-spring-boot-starter:6.7.0"){
                exclude(group = "commons-logging", module = "commons-logging")
            }
            implementation("org.telegram:telegrambots-abilities:6.7.0"){
                exclude(group = "commons-logging", module = "commons-logging")
            }
            implementation("com.zaxxer:HikariCP")
        }
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}