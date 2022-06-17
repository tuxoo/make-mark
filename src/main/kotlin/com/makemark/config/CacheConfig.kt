package com.makemark.config

import com.github.benmanes.caffeine.cache.AsyncCache
import com.github.benmanes.caffeine.cache.Caffeine
import com.makemark.config.property.CacheProperty
import com.makemark.model.dto.UserDTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import java.util.concurrent.TimeUnit

@Configuration
class CacheConfig(
    private val cacheProperty: CacheProperty
) {

    @Bean
    fun userCache(): AsyncCache<UUID, UserDTO> = Caffeine.newBuilder()
        .maximumSize(cacheProperty.userMaximumSize)
        .expireAfterAccess(cacheProperty.userExpiredTimeHours, TimeUnit.HOURS)
        .buildAsync()
}