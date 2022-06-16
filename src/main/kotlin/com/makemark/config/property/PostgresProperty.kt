package com.makemark.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "postgres")
data class PostgresProperty(
    val url: String,
    val username: String,
    val password: String,
    val maxActiveConnections: Int,
    val maxPendingQueries: Int
)
