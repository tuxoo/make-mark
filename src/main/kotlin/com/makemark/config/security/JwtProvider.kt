package com.makemark.config.security

import com.makemark.config.property.ApplicationProperty
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Component
class JwtProvider(
    private val applicationProperty: ApplicationProperty,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(JwtProvider::class.java)
    }

    fun generateToken(login: String): String =
        Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant())
            .run {
                Jwts.builder()
                    .setExpiration(this)
                    .setSubject(login)
                    .signWith(SignatureAlgorithm.HS256, applicationProperty.jwtSigningKey)
                    .setHeaderParam("typ", "JWT")
                    .compact();
            }

    fun validateToken(token: String): Boolean =
        try {
            Jwts.parser().setSigningKey(applicationProperty.jwtSigningKey).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            logger.info("invalid token")
        }.run {
            false
        }

    fun getUserIdFromToken(token: String): String =
        Jwts.parser().setSigningKey(applicationProperty.jwtSigningKey).parseClaimsJws(token).body
            .run {
                this.subject
            }
}