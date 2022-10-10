package com.makemark.service

import com.makemark.config.property.ApplicationProperty
import com.makemark.config.property.SessionProperty
import com.makemark.model.entity.Session
import com.makemark.model.entity.User
import com.makemark.model.exception.SessionNotFoundException
import com.makemark.repository.SessionRepository
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
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
                sessionRepository.deleteAll(this).then().subscribe()
            }
        }.run {
            sessionRepository.save(
                Session(
                    userId = user.id.toString(),
                    expiresAt = Instant.now().plus(applicationProperty.refreshTokenTTL)
                )
            ).awaitSingle().refreshToken
        }

    suspend fun updateToken(token: UUID): Session =
        getByTokenOrThrow(token).run {
            sessionRepository.save(
                Session(
                    id = this.id,
                    userId = this.userId,
                    expiresAt = this.expiresAt
                )
            ).awaitSingle()
        }

    suspend fun getByTokenOrThrow(token: UUID): Session =
        sessionRepository.findByRefreshToken(token)
            .switchIfEmpty(Mono.error(SessionNotFoundException("Session not found by refresh token")))
            .awaitSingle()


    suspend fun getAllByUserIdOrThrow(userId: String): List<Session> =
        sessionRepository.findAllByUserId(userId)
            .collectList()
            .awaitLast()

    suspend fun delete(session: Session) =
        sessionRepository.delete(session).then().subscribe()
}