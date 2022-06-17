package com.makemark.model.enums

import java.time.Duration

enum class Cache(
    val meaning: String,
    val ttl: Duration
) {
    USER("user", Duration.ofMinutes(10))
}