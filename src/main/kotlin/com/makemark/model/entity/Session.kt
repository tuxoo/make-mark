package com.makemark.model.entity

import java.time.Instant
import java.util.*

data class Session(
    val id: Long,
    val refreshToken: UUID = UUID.randomUUID(),
    val expiresAt: Instant,

    val userId: UUID
)