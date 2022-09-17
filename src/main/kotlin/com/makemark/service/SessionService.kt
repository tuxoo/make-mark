package com.makemark.service

import com.makemark.config.property.ApplicationProperty
import com.makemark.config.property.SessionProperty
import com.makemark.model.entity.Session
import com.makemark.model.entity.User
import com.makemark.repository.SessionRepository
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class SessionService(
    private val applicationProperty: ApplicationProperty,
    private val sessionProperty: SessionProperty,
    private val sessionRepository: SessionRepository,
) {

    suspend fun create(user: User): UUID =
        with(getAllByUserIdOrThrow(user.id.toString())) {
            if (size >= sessionProperty.max) {
                sessionRepository.deleteAll(this).block()
            }
        }.run {
            sessionRepository.save(
                Session(
                    userId = user.id.toString(),
                    expiresAt = Instant.now().plus(applicationProperty.refreshTokenTTL)
                )
            ).awaitSingle().refreshToken
        }

    suspend fun getAllByUserIdOrThrow(userId: String): List<Session> =
        sessionRepository.findAllByUserId(userId)
            .collectList()
            .awaitLast()
}