package com.ertools.utils

import com.ertools.dto.CodeList
import com.typesafe.config.ConfigFactory

object Resources {
    private val config = ConfigFactory.load("application.conf")
    val keyLength: Int = config.getInt("key.length")
    val keySize: Int = config.getInt("key.size")
    val keyOpacity: Double = config.getDouble("key.opacity")
    val keys = YamlManager.readYamlObject(Constants.KEYS_PATH, CodeList::class.java)
}
