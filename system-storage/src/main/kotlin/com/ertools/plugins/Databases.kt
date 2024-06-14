package com.ertools.plugins

import com.ertools.commons.Utils
import com.ertools.domain.ImageRepository
import com.ertools.domain.UserRepository
import com.ertools.infrastructure.ImageRepositoryImpl
import com.ertools.infrastructure.UserRepositoryImpl
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureDatabases() {
    install(Koin) {
        slf4jLogger()

        modules(module {
            single {
                MongoClient.create(
                environment.config.propertyOrNull(Utils.DATABASE_URL_PATH)?.getString() ?: throw RuntimeException("Failed to access MongoDB URI.")
            ) }
            single { get<MongoClient>().getDatabase(environment.config.property(Utils.DATABASE_NAME_PATH).getString()) }
        }, module {
            single<UserRepository> { UserRepositoryImpl(get()) }
            single<ImageRepository> { ImageRepositoryImpl(get()) }
        })
    }
}
