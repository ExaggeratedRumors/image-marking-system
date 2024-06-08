package com.ertools.infrastructure

import com.ertools.commons.Utils
import com.ertools.domain.User
import com.ertools.domain.UserRepository
import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import org.bson.BsonValue
import org.bson.types.ObjectId

class UserRepositoryImpl(
    private val mongoDatabase: MongoDatabase
) : UserRepository {
    override suspend fun insertOne(user: User): BsonValue? {
        try {
            val result = mongoDatabase
                .getCollection<User>(Utils.DATABASE_USER_COLLECTION)
                .insertOne(user)
            return result.insertedId
        } catch (e: MongoException) {
            System.err.println("Unable to insert due to an error: $e")
            return null
        }
    }

    override suspend fun deleteById(objectId: ObjectId): Long {
        try {
            val result = mongoDatabase
                .getCollection<User>(Utils.DATABASE_USER_COLLECTION)
                .deleteOne(Filters.eq("_id", objectId))
            return result.deletedCount
        } catch (e: MongoException) {
            System.err.println("Unable to delete due to an error: $e")
            return 0
        }
    }

    override suspend fun findById(objectId: ObjectId): User? =
        mongoDatabase
            .getCollection<User>(Utils.DATABASE_USER_COLLECTION)
            .withDocumentClass<User>()
            .find(Filters.eq("_id", objectId))
            .firstOrNull()

    override suspend fun findByLogin(login: String): User? =
        mongoDatabase
            .getCollection<User>(Utils.DATABASE_USER_COLLECTION)
            .withDocumentClass<User>()
            .find(Filters.eq("_login", login))
            .firstOrNull()

    override suspend fun updateOne(objectId: ObjectId, user: User): Long {
        try {
            val query = Filters.eq("_id", objectId)
            val updates = Updates.combine(
                Updates.set(User::name.name, user.name),
                Updates.set(User::login.name, user.login),
                Updates.set(User::password.name, user.password),
                Updates.set(User::code.name, user.code)
            )
            val options = UpdateOptions().upsert(true)
            val result =
                mongoDatabase.getCollection<User>(Utils.DATABASE_USER_COLLECTION)
                    .updateOne(query, updates, options)

            return result.modifiedCount
        } catch (e: MongoException) {
            System.err.println("Unable to update due to an error: $e")
        }
        return 0
    }
}