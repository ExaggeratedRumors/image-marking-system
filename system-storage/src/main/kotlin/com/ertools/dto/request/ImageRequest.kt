package com.ertools.dto.request

import com.ertools.dto.entity.Image
import org.bson.types.ObjectId

data class ImageRequest (
    val name: String,
    val data: ByteArray,
    val date: Long
) {
    fun toDataObject(): Image = Image(
        id = ObjectId(),
        name = name,
        data = data,
        date = date,
        fetchAmount = 0
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ImageRequest
        if (name != other.name) return false
        if (!data.contentEquals(other.data)) return false
        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + date.hashCode()
        return result
    }
}