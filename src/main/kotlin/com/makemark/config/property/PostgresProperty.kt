package com.makemark.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "postgres")
data class PostgresProperty(
    val url: String,
    val host: String,
    val port: Int,
    val db: String,
    val username: String,
    val password: String,
    val maxActiveConnections: Int,
    val maxPendingQueries: Int,
    val maxConnectionTtl: Long,
    val maxIdleTime: Long
)
