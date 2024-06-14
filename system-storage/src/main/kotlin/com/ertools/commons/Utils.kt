package com.ertools.commons

object Utils {
    /** Database **/
    const val DATABASE_URL_PATH = "ktor.mongo.uri"
    const val DATABASE_NAME_PATH = "ktor.mongo.database"
    const val DATABASE_USER_COLLECTION = "user"
    const val DATABASE_IMAGE_COLLECTION = "image"

    /** Swagger **/
    const val SWAGGER_VERSION = "4.15.5"
    const val SWAGGER_FILE_PATH = "openapi/documentation.yaml"
    const val SWAGGER_PATH = "swagger-ui"

    /** Security **/
    const val JWT_NAME = "auth_jwt"
    const val JWT_ISSUER = "http://0.0.0.0:8082/"
    const val JWT_AUDIENCE = "http://0.0.0.0:8082/storage-audience"
    const val JWT_REALM = "system_storage"
    const val JWT_SECRET = "secret"
    const val JWT_USER = "system-storage-user"
    const val JWT_PASSWORD = "system-storage-password"

}