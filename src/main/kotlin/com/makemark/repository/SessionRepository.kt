package com.makemark.repository

import com.github.jasync.sql.db.SuspendingConnection
import com.makemark.extension.*
import com.makemark.model.entity.Session
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Repository
class SessionRepository {

    companion object {
        const val sessionTable = "session"
    }

    suspend fun save(connection: SuspendingConnection, expiresAt: Instant, userId: UUID): UUID =
        connection.execute(
            "INSERT INTO $sessionTable (expires_at, user_id) VALUES (?, ?) RETURNING refresh_token",
            listOf(
                Timestamp.from(expiresAt),
                userId
            )
        ).rows[0].getNonNullableUUID("refresh_token")

    suspend fun findAllByUserId(connection: SuspendingConnection, userId: UUID): List<Session> =
        connection.selectList(
            "SELECT id, refresh_token, expires_at, user_id FROM $sessionTable WHERE user_id=?",
            listOf(userId)
        ) {
            Session(
                id = it.getNonNullableLong("id"),
                refreshToken = it.getNonNullableUUID("refresh_token"),
                expiresAt = it.getNonNullableInstant("expires_at"),
                userId = it.getNonNullableUUID("user_id")
            )
        }

    suspend fun deleteAllByUserId(connection: SuspendingConnection, userId: UUID): Unit =
        with(userId) {
            connection.execute(
                "DELETE FROM $sessionTable WHERE user_id=?",
                listOf(this)
            )
        }
}