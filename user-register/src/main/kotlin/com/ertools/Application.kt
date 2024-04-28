package com.ertools

import com.ertools.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)


fun Application.module() {
    configureSecurity()
    configureRouting()

}
