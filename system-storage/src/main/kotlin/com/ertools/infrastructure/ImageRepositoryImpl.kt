package com.ertools.infrastructure

import com.ertools.commons.Utils
import com.ertools.domain.ImageRepository
import com.ertools.dto.entity.Image
import com.ertools.dto.entity.User
import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.BsonValue

class ImageRepositoryImpl(
    private val mongoDatabase: MongoDatabase
): ImageRepository {
    override suspend fun insert(image: Image): BsonValue? {
        try {
            if(mongoDatabase
                    .getCollection<Image>(Utils.DATABASE_IMAGE_COLLECTION)
                    .withDocumentClass<User>()
                    .find(Filters.eq("name", image.name))
                    .firstOrNull() != null
            ) {
                System.err.println("Name is already registered")
                return null
            }
            val result = mongoDatabase
                .getCollection<Image>(Utils.DATABASE_IMAGE_COLLECTION)
                .insertOne(image)
            return result.insertedId
        } catch (e: MongoException) {
            System.err.println("Unable to insert due to an error: $e")
            return null
        }
    }

    override suspend fun deleteByName(name: String): Long {
        try {
            val result = mongoDatabase
                .getCollection<Image>(Utils.DATABASE_IMAGE_COLLECTION)
                .deleteOne(Filters.eq("name", name))
            return result.deletedCount
        } catch (e: MongoException) {
            System.err.println("Unable to delete due to an error: $e")
            return 0
        }
    }

    override suspend fun selectAll(): List<Image> =
        mongoDatabase
            .getCollection<Image>(Utils.DATABASE_IMAGE_COLLECTION)
            .withDocumentClass<Image>()
            .find()
            .toList()

    override suspend fun findByName(name: String): Image? = mongoDatabase
            .getCollection<Image>(Utils.DATABASE_IMAGE_COLLECTION)
            .withDocumentClass<Image>()
            .find(Filters.eq("name", name))
            .firstOrNull()

    override suspend fun updateByName(name: String, image: Image): Long {
        try {
            val query = Filters.eq("name", name)
            val updates = Updates.combine(
                Updates.set(Image::name.name, image.name),
                Updates.set(Image::data.name, image.data),
                Updates.set(Image::date.name, image.date),
                Updates.set(Image::fetchAmount.name, image.fetchAmount)
            )
            val options = UpdateOptions().upsert(true)
            val result =
                mongoDatabase.getCollection<User>(Utils.DATABASE_IMAGE_COLLECTION)
                    .updateOne(query, updates, options)
            return result.modifiedCount
        } catch (e: MongoException) {
            System.err.println("Unable to update due to an error: $e")
        }
        return 0
    }

}