package com.makemark.model.enums

enum class Auth(
    val value: String,
    val length: Int
) {
    AUTHORIZATION("Authorization", 13),
    BEARER("Bearer ", 7)
}