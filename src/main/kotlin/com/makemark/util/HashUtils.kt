package com.makemark.util

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object HashUtils {

    private val mdSHA1: MessageDigest = MessageDigest.getInstance("SHA-1")

    fun hashSHA1(password: String): String =
        try {
            val bytes = mdSHA1.digest(password.toByteArray(StandardCharsets.UTF_8))
            val sb = StringBuilder()
            for (b in bytes) sb.append(String.format("%02x", b))
            sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            error("error occurred hashing")
        }
}