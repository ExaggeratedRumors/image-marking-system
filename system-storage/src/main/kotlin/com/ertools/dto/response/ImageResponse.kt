package com.ertools.dto.response

data class ImageResponse (
    val id: String,
    val name: String,
    val data: ByteArray,
    val date: Long,
    val fetchAmount: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ImageResponse
        if (!data.contentEquals(other.data)) return false
        if (id != other.id) return false
        if (name != other.name) return false
        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + date.hashCode()
        return result
    }
}