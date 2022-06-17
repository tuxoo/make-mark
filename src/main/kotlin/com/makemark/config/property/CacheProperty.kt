package com.makemark.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "cache")
data class CacheProperty(
    val userMaximumSize: Long,
    val userExpiredTimeHours: Long
)