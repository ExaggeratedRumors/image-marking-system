@file:Suppress("PropertyName")
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project
val koin_ksp_version: String by project
val hoplite_version:String by project
val kmongo_version: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
    id("com.google.devtools.ksp") version "1.9.23-1.0.19"
}

group = "com.ertools"
version = "0.1.0"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    /** Core **/
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-html-builder:$ktor_version")
    //implementation("io.ktor:ktor-client-cio:$ktor_version")
    //implementation("io.ktor:ktor-server-sessions:2.3.0")

    /* Documentation */
    implementation("io.ktor:ktor-server-swagger-jvm")

    /** Serialization **/
    /*implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-compression-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson:2.3.1")*/
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-gson-jvm")
    implementation("io.ktor:ktor-server-tomcat-jvm")

    /** Database **/
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.10.1")
    /*implementation("org.litote.kmongo:kmongo-coroutine-core:$kmongo_version")
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:$kmongo_version")
    implementation("org.litote.kmongo:kmongo-id:$kmongo_version")
    implementation("org.litote.kmongo:kmongo-core:$kmongo_version")
    implementation("org.litote.kmongo:kmongo-async:$kmongo_version")
    implementation("org.litote.kmongo:kmongo:$kmongo_version")*/

    /** Authentication **/
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")

    /** Koin **/
    //implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    /*testImplementation("io.insert-koin:koin-test:$koin_version")
    testImplementation("io.insert-koin:koin-test-junit5:$koin_version")
    implementation("io.insert-koin:koin-annotations:$koin_ksp_version")
    ksp("io.insert-koin:koin-ksp-compiler:$koin_ksp_version")*/

    /** Hoplite **/
    //implementation("com.sksamuel.hoplite:hoplite-core:$hoplite_version")
    //implementation("com.sksamuel.hoplite:hoplite-yaml:$hoplite_version")
    /*implementation("io.github.smiley4:ktor-swagger-ui:2.2.0") {
        exclude(group = "org.slf4j", module = "slf4j-api")
        exclude(group = "org.yaml", module = "snakeyaml")
    }*/

    /** Tests **/
    implementation("ch.qos.logback:logback-classic:$logback_version")
    /*testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")*/
}

sourceSets {
    main { java.srcDirs("${project.buildDir}/generated/ksp/main/kotlin") }
    test { java.srcDirs("${project.buildDir}/generated/ksp/test/kotlin") }
}

java {
    sourceCompatibility = JavaVersion.VERSION_20
    targetCompatibility = JavaVersion.VERSION_20
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "20"
    }
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
        localImageName.set("system-storage-image")
        imageTag.set("alpha")
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}