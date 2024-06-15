import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.10"
}

group = "com.ertools"
version = "0.0.1"

application {
    mainClass.set("com.ertools.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    /** Core **/
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-client-cio:2.3.0")
    implementation("io.ktor:ktor-client-core:2.3.0")

    /** Serialization **/
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson-jvm:2.0.3")
    implementation("com.typesafe:config:1.4.1")

    /** Logging **/
    implementation("ch.qos.logback:logback-classic:$logback_version")

    /** Test **/
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}


tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("shadow")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "com.github.csolem.gradle.shadow.kotlin.example.App"))
        }
    }
}

ktor {
    docker {
        localImageName.set("user-register-img")
        imageTag.set("alpha")
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
