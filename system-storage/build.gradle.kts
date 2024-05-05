import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
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
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-html-builder:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")

    /** Serialization **/
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-compression-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson:2.3.1")

    /** Database **/
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.10.1")

    /** Authentication **/
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")

    /** Koin **/
    val koinVersion = "3.3.2"
    implementation("io.insert-koin:koin-core:$koinVersion")
    testImplementation("io.insert-koin:koin-test:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")
    val koinKspVersion = "1.1.0"
    implementation("io.insert-koin:koin-annotations:$koinKspVersion")
    ksp("io.insert-koin:koin-ksp-compiler:$koinKspVersion")

    /** Hoplite **/
    val hopliteVersion = "2.6.5"
    implementation("com.sksamuel.hoplite:hoplite-core:$hopliteVersion")
    implementation("com.sksamuel.hoplite:hoplite-yaml:$hopliteVersion")
    implementation("io.github.smiley4:ktor-swagger-ui:2.2.0") {
        exclude(group = "org.slf4j", module = "slf4j-api")
        exclude(group = "org.yaml", module = "snakeyaml")
    }

    /** Tests **/
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
}

sourceSets {
    main { java.srcDirs("${project.buildDir}/generated/ksp/main/kotlin") }
    test { java.srcDirs("${project.buildDir}/generated/ksp/test/kotlin") }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}