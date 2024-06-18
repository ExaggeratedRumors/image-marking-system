package com.ertools.dto.response

data class ImageResponse (
    val id: String,
    val name: String,
    val data: String,
    val date: Long,
    val fetchAmount: Int
)