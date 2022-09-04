package com.makemark.config.property

import com.makemark.util.HashUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties(prefix = "application")
data class ApplicationProperty(
    val url: String,
    val apiPath: String,
    val hashSalt: String,
    val jwtSigningKey: String,
    val accessTokenTTL: Duration,
    val refreshTokenTTL: Duration
) {

    @Bean
    fun hashSalt(): HashUtils {
        return HashUtils.also {
            it.salt = hashSalt
        }
    }
}
