package com.makemark.util

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object HashUtils {

    fun HashSHA1(password: String): String =
        try {
            val md = MessageDigest.getInstance("SHA-1")
            val bytes = md.digest(password.toByteArray(StandardCharsets.UTF_8))
            val sb = StringBuilder()
            for (b in bytes) sb.append(String.format("%02x", b))
            sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            ""
        }
}