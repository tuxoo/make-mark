package com.makemark.model.entity

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("mark")
data class Mark(
    @Id
    val id: ObjectId = ObjectId.get(),

    val title: String,
    val text: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val createdAt: Instant,

    val userId: ObjectId
)
