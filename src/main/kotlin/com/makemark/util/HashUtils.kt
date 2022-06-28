package com.makemark.util

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object HashUtils {

    lateinit var salt: String
    private val mdSHA1: MessageDigest = MessageDigest.getInstance("SHA-1")

    fun hashSHA1(password: String): String {
        mdSHA1.update(salt.toByteArray())
        val bytes = mdSHA1.digest(password.toByteArray(StandardCharsets.UTF_8))
        val sb = StringBuilder()
        for (b in bytes) sb.append(String.format("%02x", b))
        return sb.toString()
    }
}