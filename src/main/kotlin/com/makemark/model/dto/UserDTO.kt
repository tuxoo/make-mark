package com.makemark.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.makemark.model.enums.Role
import java.time.Instant
import java.util.*

data class UserDTO(
    val id: UUID,
    val name: String,
    @JsonProperty("email") val loginEmail: String,
    val registeredAt: Instant,
    val visitedAt: Instant,
    val role: Role,
    val isEnabled: Boolean
)
