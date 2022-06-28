package com.makemark.config.property

import com.makemark.util.HashUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean

@ConstructorBinding
@ConfigurationProperties(prefix = "app")
data class ApplicationProperty(
    val url: String,
    val apiPath: String,
    val hashSalt: String,
    val jwtSigningKey: String,
    val tokenTTL: Long
) {

    @Bean
    fun hashSalt(): HashUtils {
        return HashUtils.also {
            it.salt = hashSalt
        }
    }
}
