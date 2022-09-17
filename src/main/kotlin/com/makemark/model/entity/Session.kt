package com.makemark.model.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*

@Document("session")
data class Session(
    @Id
    val id: ObjectId = ObjectId.get(),
    val refreshToken: UUID = UUID.randomUUID(),
    val expiresAt: Instant,

    val userId: ObjectId
)