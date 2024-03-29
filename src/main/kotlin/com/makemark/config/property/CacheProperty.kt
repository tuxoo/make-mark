package com.makemark.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties(prefix = "cache")
data class CacheProperty(
    val userMaximumSize: Long,
    val userExpiredTime: Duration
)