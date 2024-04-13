package com.ertools.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Configuration (
    val keyLength: Int = 10,
    val keySize: Int = 10,
    val keyOpacity: Double = 0.1
)