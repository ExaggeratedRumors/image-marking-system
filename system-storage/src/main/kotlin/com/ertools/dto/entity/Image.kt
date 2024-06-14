package com.ertools.dto.entity

import com.ertools.dto.response.ImageResponse
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Image (
    @BsonId
    val id: ObjectId = ObjectId(),
    val name: String,
    val data: String,
    val date: Long,
    val fetchAmount: Int
) {
    fun toResponse() = ImageResponse(
        id = id.toString(),
        name = name,
        data = data,
        date = date,
        fetchAmount = fetchAmount
    )
}