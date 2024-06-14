package com.ertools.domain

import com.ertools.dto.entity.Image
import org.bson.BsonValue

interface ImageRepository {
    suspend fun insert(image: Image): BsonValue?
    suspend fun deleteByName(name: String): Long
    suspend fun selectAll(): List<Image>
    suspend fun findByName(name: String): Image?
    suspend fun updateByName(name: String, image: Image): Long
}