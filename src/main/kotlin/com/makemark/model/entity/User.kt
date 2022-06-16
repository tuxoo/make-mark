package com.makemark.model.entity

import com.makemark.model.enums.Role
import java.time.Instant
import java.util.*


data class User(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val loginEmail: String,
    val passwordHash: String,
    val registeredAt: Instant,
    val visitedAt: Instant,
    val role: Role = Role.USER,
    val isEnabled: Boolean = false
)
