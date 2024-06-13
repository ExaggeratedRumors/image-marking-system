package com.ertools.domain

import com.ertools.dto.entity.User
import org.bson.BsonValue
import org.bson.types.ObjectId

interface UserRepository {
    suspend fun insertOne(user: User): BsonValue?
    suspend fun deleteByLogin(login: String): Long
    suspend fun findByLogin(login: String): User?
    suspend fun updateOne(login: String, user: User): Long
}