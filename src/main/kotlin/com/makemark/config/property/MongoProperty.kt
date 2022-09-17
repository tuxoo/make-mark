package com.makemark.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.data.mongodb")
data class MongoProperty(
    val host: String,
    val port: Int,
    val userName: String,
    val password: String,
    val database: String
)
