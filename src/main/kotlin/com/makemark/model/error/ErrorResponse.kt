package com.makemark.model.error

import java.time.Instant

data class ErrorResponse(
    val message: String,
    val errorTime: Instant = Instant.now()
)
