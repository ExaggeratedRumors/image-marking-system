package com.ertools.utils

import com.ertools.dto.CodeList
import com.ertools.dto.Configuration
import com.ertools.model.CodeManager

object Resources {
    val keys = YamlManager.readYamlObject(Constants.KEYS_PATH, CodeList::class.java)
    val configuration = YamlManager.readYamlObject(Constants.CONFIGURATION_PATH, Configuration::class.java)
}
