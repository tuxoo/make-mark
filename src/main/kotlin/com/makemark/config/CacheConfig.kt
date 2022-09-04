package com.makemark.config

import com.github.benmanes.caffeine.cache.AsyncCache
import com.github.benmanes.caffeine.cache.Caffeine
import com.makemark.config.property.CacheProperty
import com.makemark.model.dto.UserDTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class CacheConfig(
    private val property: CacheProperty
) {

    @Bean
    fun userCache(): AsyncCache<UUID, UserDTO> = Caffeine.newBuilder()
        .maximumSize(property.userMaximumSize)
        .expireAfterAccess(property.userExpiredTime)
        .buildAsync()
}