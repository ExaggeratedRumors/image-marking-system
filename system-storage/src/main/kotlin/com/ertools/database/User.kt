package com.ertools.database

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
data class User (
    val name: String,
    val login: String,
    val password: String,
    val code: String,
    @BsonId
    var id: String = ObjectId().toString()
)