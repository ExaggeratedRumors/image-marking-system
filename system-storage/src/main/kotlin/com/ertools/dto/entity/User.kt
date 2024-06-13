package com.ertools.dto.entity

import com.ertools.dto.response.UserResponse
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
data class User (
    @BsonId
    val id: ObjectId = ObjectId(),
    val name: String,
    val login: String,
    val password: String,
    val code: String,
) {
    fun toResponse() = UserResponse(
        id = id.toString(),
        name = name,
        login = login,
        password = password,
        code = code
    )
}