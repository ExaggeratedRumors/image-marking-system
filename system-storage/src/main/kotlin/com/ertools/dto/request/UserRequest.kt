package com.ertools.dto.request

import com.ertools.dto.entity.User
import org.bson.types.ObjectId

data class UserRequest(
    val name: String,
    val login: String,
    val password: String,
    val code: String
) {
    fun toDataObject(): User = User(
        id = ObjectId(),
        name = name,
        login = login,
        password = password,
        code = code
    )
}