package com.makemark.model.dto

import java.time.Instant

data class MarkSlimDto(
    val id: Long,
    val title: String,
    val text: String,
    val createdAt: Instant
)
