package com.ertools.plugins

import com.ertools.domain.UserRepository
import com.ertools.infrastructure.UserRepositoryImpl
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.server.application.*
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureDatabases() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single {
                MongoClient.create(
                    environment
                        .config
                        .propertyOrNull("ktor.mongo.uri")
                        ?.getString() ?: throw RuntimeException("Failed to access MongoDB URI.")
                )
            }
            single {
                get<MongoClient>()
                    .getDatabase(environment.config.property("ktor.mongo.database").getString())
            }
        }, module {
            single<UserRepository> { UserRepositoryImpl(get()) }
        })
    }
}
