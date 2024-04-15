package com.ertools.model

import com.ertools.dto.CodeList
import com.ertools.dto.UserEntity
import com.ertools.utils.Constants
import com.ertools.utils.Resources
import com.ertools.utils.YamlManager
import java.io.*
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.KeyGenerator
import kotlin.system.exitProcess

class CodeManager {
    fun generateKey(keyLength: Int): String {
        try {
            val key = KeyGenerator
                .getInstance(Constants.KEY_ALGORITHM)
                .apply { this.init(256) }
                .generateKey()

            return Base64
                .getEncoder()
                .encodeToString(key.encoded)
                .substring(0, keyLength)
                .replace("\\", "o")
                .replace("/", "i")
        } catch(e: NoSuchAlgorithmException) {
            println("ENGINE: Entered key generating algorithm is incorrect.")
            exitProcess(1)
        }
    }

    private fun serialize() {
        try {
            YamlManager.writeYamlObject(Constants.KEYS_PATH, Resources.keys)
        } catch (e: Exception) {
            println("ENGINE: Cannot save list object.")
            e.printStackTrace()
        }
    }

    fun deserialize(): CodeList {
        val keysFile = File(Constants.KEYS_PATH)
        if(!keysFile.exists()) return CodeList()
        return try {
            YamlManager.readYamlObject(Constants.KEYS_PATH, CodeList::class.java)
        } catch (e: Exception) {
            println("ENGINE: Error occurred with reading keys file.")
            e.printStackTrace()
            CodeList()
        }
    }

    fun addEntity(userId: Long, userName: String): String{
        val key = generateKey(Resources.keyLength)
        Resources.keys.modify {
            this.add(UserEntity(userId, userName, key))
        }
        serialize()
        return key
    }

    fun getUserNameByCode(userCode: String): String? {
        Resources.keys.userEntities.forEach {
            if(it.code == userCode) return it.name
        }
        return null
    }

    fun getUserCodeById(userId: Long): String? {
        Resources.keys.userEntities.forEach {
            if(it.id == userId) return it.code
        }
        return null
    }

    fun getUsersList(): String {
        var usersList = String()
        Resources.keys.userEntities.forEach {
            usersList = usersList.plus("${it.name} ${it.id} ${it.code}\n")
        }
        return usersList
    }

    fun clearUsersList() {
        Resources.keys.userEntities.clear()
        serialize()
    }
}