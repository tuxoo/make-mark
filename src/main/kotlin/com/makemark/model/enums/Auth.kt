package com.makemark.model.enums

enum class Auth(
    val meaning: String,
    val length: Int
) {
    AUTHORIZATION("Authorization", 13),
    BEARER("Bearer ", 7)
}