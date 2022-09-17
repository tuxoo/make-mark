package com.makemark.model.entity

import com.makemark.model.enums.Role
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("user")
data class User(
    @Id
    val id: ObjectId = ObjectId.get(),

    val firstName: String,

    val lastName: String,

    @Indexed(unique = true)
    val email: String,

    val passwordHash: String? = null,

    val registeredAt: Instant = Instant.now(),

    val visitedAt: Instant = Instant.now(),

    val role: Role = Role.USER,

    val isEnabled: Boolean = false,
)
