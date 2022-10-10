package com.makemark.model.dto

import java.util.*

data class TokenWrapper(
    val accessToken: String?,
    val refreshToken: UUID
)
