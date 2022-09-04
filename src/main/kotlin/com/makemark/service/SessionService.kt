package com.makemark.service

import com.github.jasync.sql.db.SuspendingConnection
import com.makemark.config.property.ApplicationProperty
import com.makemark.config.property.SessionProperty
import com.makemark.model.entity.User
import com.makemark.repository.SessionRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class SessionService(
    private val applicationProperty: ApplicationProperty,
    private val sessionProperty: SessionProperty,
    private val sessionRepository: SessionRepository,
    private val pool: SuspendingConnection,
) {

    suspend fun createSession(user: User): UUID =
        with(sessionRepository.findAllByUserId(pool, user.id)) {
            if (size >= sessionProperty.max) {
                sessionRepository.deleteAllByUserId(pool, user.id)
            }
        }.run {
            sessionRepository.save(
                connection = pool,
                expiresAt = Instant.now().plus(applicationProperty.refreshTokenTTL),
                userId = user.id
            )
        }
}