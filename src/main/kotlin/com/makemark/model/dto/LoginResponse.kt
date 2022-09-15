package com.makemark.model.dto

import java.util.*

data class LoginResponse(
    val accessToken: String,
    val refreshToken: UUID,
    val user: UserDto,
)
