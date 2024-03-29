package com.makemark.config

import com.github.benmanes.caffeine.cache.AsyncCache
import com.github.benmanes.caffeine.cache.Caffeine
import com.makemark.config.property.CacheProperty
import com.makemark.model.entity.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CacheConfig(
    private val property: CacheProperty
) {

    @Bean
    fun userCache(): AsyncCache<String, User> = Caffeine.newBuilder()
        .maximumSize(property.userMaximumSize)
        .expireAfterAccess(property.userExpiredTime)
        .buildAsync()
}