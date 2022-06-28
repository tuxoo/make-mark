package com.makemark.config.security

import com.makemark.config.property.ApplicationProperty
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class JwtProvider(
    private val property: ApplicationProperty,
) {

    private val key = Keys.hmacShaKeyFor(property.jwtSigningKey.toByteArray())
    private val parser = Jwts.parserBuilder().setSigningKey(key).build()
    
    fun generateToken(login: String): BearerToken = Jwts.builder()
        .setExpiration(Date.from(Instant.now().plus(property.tokenTTL, ChronoUnit.HOURS)))
        .setSubject(login)
        .setIssuedAt(Date.from(Instant.now()))
        .signWith(key)
        .run {
            BearerToken(this.compact())
        }

    fun getUserIdFromToken(token: BearerToken): UUID =
        UUID.fromString(parser.parseClaimsJws(token.value).body.subject)


    fun validateToken(token: BearerToken, userDetails: AppUserDetails): Boolean {
        val claims = parser.parseClaimsJws(token.value).body
        val unexpired = claims.expiration.after(Date.from(Instant.now()))
        val userId = UUID.fromString(claims.subject)

        return unexpired && (userId == userDetails.getId())
    }
}