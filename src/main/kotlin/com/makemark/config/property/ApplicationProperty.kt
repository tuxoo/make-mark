package com.makemark.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app")
data class ApplicationProperty(
    val url: String,
    val apiPath: String,
    val jwtSigningKey: String,
    val tokenTTL: Long
)
