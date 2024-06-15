package com.ertools.commons

import com.typesafe.config.ConfigFactory

object Utils {
    /** Configuration **/
    private val config = ConfigFactory.load("application.conf")
    val KEY_LENGTH: Int = config.getInt("ktor.encryption.key_length")
    val ILLEGAL_CHARS: CharArray = config.getString("ktor.encryption.illegal_chars").toCharArray()

    /** Encryption **/
    const val ENCRYPTION_ALGORITHM: String = "HmacSHA384"

    /** Endpoints **/
    val SYSTEM_STORAGE_ENDPOINT: String = config.getString("ktor.system-storage.url")
}
