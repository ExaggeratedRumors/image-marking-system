package com.ertools.dto.request

import com.ertools.dto.entity.Image
import org.bson.types.ObjectId

data class ImageRequest (
    val name: String,
    val data: String
) {
    fun toDataObject(): Image = Image(
        id = ObjectId(),
        name = name,
        data = data,
        date = System.currentTimeMillis(),
        fetchAmount = 0
    )
}