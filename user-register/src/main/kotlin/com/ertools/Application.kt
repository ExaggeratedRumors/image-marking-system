package com.ertools

import com.ertools.plugins.configureCORS
import com.ertools.plugins.configureRouting
import com.ertools.plugins.configureSecurity
import com.ertools.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureSecurity()
    configureRouting()
    configureCORS()
}
