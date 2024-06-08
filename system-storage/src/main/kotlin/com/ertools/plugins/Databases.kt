package com.ertools.plugins

import com.ertools.commons.Utils
import com.ertools.domain.User
import com.ertools.domain.UserRepository
import com.ertools.infrastructure.UserRepositoryImpl
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import io.ktor.server.application.*
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

fun Application.configureDatabases() {
    install(Koin) {
        slf4jLogger()
        startKoin {
            modules(module {
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
            })
        }
    }
}
