package com.ertools.dto.entity

import com.ertools.dto.response.ImageResponse
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Image (
    @BsonId
    val id: ObjectId = ObjectId(),
    val name: String,
    val data: ByteArray,
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Image
        if (id != other.id) return false
        if (name != other.name) return false
        if (!data.contentEquals(other.data)) return false
        if (date != other.date) return false
        if (fetchAmount != other.fetchAmount) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + fetchAmount.hashCode()
        return result
    }
}