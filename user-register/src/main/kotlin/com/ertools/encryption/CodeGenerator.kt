package com.ertools.encryption

import com.ertools.commons.Utils
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.KeyGenerator

class CodeGenerator {
    fun generateKey(keyLength: Int): String {
        try {
            val secret = KeyGenerator
                .getInstance(Utils.ENCRYPTION_ALGORITHM)
                .apply { this.init(256) }
                .generateKey()

            return Base64
                .getEncoder()
                .encodeToString(secret.encoded)
                .substring(0, keyLength)
                .filter { !Utils.ILLEGAL_CHARS.contains(it) }
        } catch(e: NoSuchAlgorithmException) {
            error("ENCRYPTION: Entered key generating algorithm is incorrect.")
        }
    }

}