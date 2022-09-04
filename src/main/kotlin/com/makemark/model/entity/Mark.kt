package com.makemark.model.entity

import java.time.Instant
import java.util.*

data class Mark(
    val id: Long? = null,
    val title: String,
    val text: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val createdAt: Instant,

    val userId: UUID
)