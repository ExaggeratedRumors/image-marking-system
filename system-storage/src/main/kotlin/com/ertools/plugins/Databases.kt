package com.ertools.plugins

import com.ertools.commons.Utils
import com.ertools.dto.entity.User
import com.ertools.domain.UserRepository
import com.ertools.infrastructure.UserRepositoryImpl
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import io.ktor.server.application.*
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import io.ktor.serialization.gson.gson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.routing
import io.ktor.server.tomcat.EngineMain
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
fun Application.configureDatabases() {
    install(Koin) {
        slf4jLogger()

        modules(module {
            single {
                println("TEST-PORT: ${environment.config.propertyOrNull("ktor.deployment.port")?.getString()}")
                println("TEST-URI: ${environment.config.propertyOrNull("ktor.mongo.uri")?.getString()}")
                MongoClient.create(
                environment.config.propertyOrNull("ktor.mongo.uri")?.getString() ?: throw RuntimeException("Failed to access MongoDB URI.")
            ) }
            single { get<MongoClient>().getDatabase(environment.config.property("ktor.mongo.database").getString()) }
        }, module {
            single<UserRepository> { UserRepositoryImpl(get()) }
        })

        /*modules(module {
            single {
                val connectionString = Utils.DATABASE_URL
                val codecRegistry: CodecRegistry = CodecRegistries.fromRegistries(
                    CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()),
                    MongoClientSettings.getDefaultCodecRegistry()
                )
                val settings = MongoClientSettings.builder()
                    .applyConnectionString(ConnectionString(connectionString))
                    .codecRegistry(codecRegistry)
                    .build()
                KMongo.createClient(settings).getDatabase(Utils.DATABASE_NAME)
            }
            single { get<com.mongodb.client.MongoDatabase>().getCollection<User>() }

        }, module {
            single<UserRepository> { UserRepositoryImpl(get()) }
        })*/
    }
}
