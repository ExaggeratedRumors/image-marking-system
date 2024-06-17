package com.ertools.commons

import com.typesafe.config.ConfigFactory

object Utils {
    /** Configuration **/
    private val config = ConfigFactory.load("application.conf")
    val KEY_LENGTH: Int = config.getInt("ktor.encryption.key_length")
    val ILLEGAL_CHARS: CharArray = config.getString("ktor.encryption.illegal_chars").toCharArray()
    val SYSTEM_STORAGE_USER_ENDPOINT: String = config.getString("ktor.system-storage.user_url")
    val SYSTEM_STORAGE_LOGIN_ENDPOINT: String = config.getString("ktor.system-storage.login_url")
    val SYSTEM_STORAGE_LOGIN: String = config.getString("ktor.system-storage.jwt_login")
    val SYSTEM_STORAGE_PASSWORD: String = config.getString("ktor.system-storage.jwt_password")
    val TOKEN_EXPIRING_TIME_MILLIS: Long = config.getLong("ktor.system-storage.jwt_expiring")

    /** Encryption **/
    const val ENCRYPTION_ALGORITHM: String = "HmacSHA384"

    /** Security **/
    const val JWT_AUTHENTICATION_NAME = "auth-jwt"
    const val JWT_AUTHENTICATION_REALM = "auth-realm"
    const val JWT_AUTHENTICATION_SECRET = "auth-secret"
    const val JWT_AUTHENTICATION_AUDIENCE = "auth-audience"
    const val JWT_AUTHENTICATION_ISSUER = "auth-issuer"
}
