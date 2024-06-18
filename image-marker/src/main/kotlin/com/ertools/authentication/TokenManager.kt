package com.ertools.authentication

import com.ertools.commons.Utils
import com.ertools.dto.request.Credentials
import com.ertools.dto.response.AuthResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.naming.AuthenticationException

class TokenManager {

    suspend fun updateToken(tokenData: TokenData, client: HttpClient) {
        if (tokenData.timestamp + Utils.TOKEN_EXPIRING_TIME_MILLIS > System.currentTimeMillis())
            return
        val loginRequest = Credentials(
            Utils.SYSTEM_STORAGE_LOGIN,
            Utils.SYSTEM_STORAGE_PASSWORD
        )
        val authResponse = client.post(Utils.SYSTEM_STORAGE_LOGIN_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }
        if(authResponse.status != HttpStatusCode.OK) {
            throw AuthenticationException()
        }
        tokenData.updateData(
            authResponse.body<AuthResponse>().token,
            System.currentTimeMillis()
        )
    }
}