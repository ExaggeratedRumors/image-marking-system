package com.ertools.commons

import com.typesafe.config.ConfigFactory

object Utils {
    /** Configuration **/
    private val config = ConfigFactory.load("application.conf")
    val SYSTEM_STORAGE_USER_ENDPOINT: String = System.getenv("SYSTEM_STORAGE_USER_ENDPOINT") ?:
    config.getString("ktor.system-storage.user_url")
    val SYSTEM_STORAGE_LOGIN_ENDPOINT: String = System.getenv("SYSTEM_STORAGE_LOGIN_ENDPOINT") ?:
    config.getString("ktor.system-storage.login_url")
    val SYSTEM_STORAGE_IMAGE_ENDPOINT: String = System.getenv("SYSTEM_STORAGE_IMAGE_ENDPOINT") ?:
    config.getString("ktor.system-storage.image_url")
    val SYSTEM_STORAGE_LOGIN: String = System.getenv("SYSTEM_STORAGE_LOGIN") ?:
        config.getString("ktor.system-storage.jwt_login")
    val SYSTEM_STORAGE_PASSWORD: String = System.getenv("SYSTEM_STORAGE_PASSWORD") ?:
        config.getString("ktor.system-storage.jwt_password")
    val TOKEN_EXPIRING_TIME_MILLIS: Long = config.getLong("ktor.system-storage.jwt_expiring")
    val KEY_SIZE: Int = config.getInt("ktor.key.size")
    val KEY_OPACITY: Float = config.getDouble("ktor.key.opacity").toFloat()

    /** Multipart data **/
    const val CLIENT_FILE_TAG = "file"

    /** Pathing **/
    const val IMAGES_PATH = "/data/images"
}
