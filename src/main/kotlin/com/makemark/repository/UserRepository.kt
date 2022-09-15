package com.makemark.repository

import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.SuspendingConnection
import com.makemark.extension.*
import com.makemark.model.entity.User
import com.makemark.model.enums.Role
import com.makemark.model.exception.UserNotFoundException
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.util.*

@Repository
class UserRepository {

    companion object {
        const val userTable = "\"user\""
    }

    private val userMapper: (RowData) -> User = {
        User(
            id = it.getNonNullableUUID("id"),
            firstName = it.getNonNullableString("first_name"),
            lastName = it.getNonNullableString("last_name"),
            email = it.getNonNullableString("email"),
            passwordHash = it.getNonNullableString("password_hash"),
            registeredAt = it.getNonNullableInstant("registered_at"),
            visitedAt = it.getNonNullableInstant("visited_at"),
            role = it.getNonNullableString("role").run { Role.valueOf(this) },
            isEnabled = it.getNonNullableBoolean("is_enabled")
        )
    }

    suspend fun save(connection: SuspendingConnection, user: User): Unit =
        with(user) {
            connection.execute(
                "INSERT INTO $userTable (first_name, last_name, email, password_hash, registered_at, visited_at, role) VALUES (?, ?, ?, ?, ?, ?, ?)",
                listOf(
                    firstName,
                    lastName,
                    email,
                    passwordHash,
                    Timestamp.from(registeredAt),
                    Timestamp.from(visitedAt),
                    role.name
                )
            )
        }

    suspend fun updateIsEnabled(connection: SuspendingConnection, id: UUID) =
        connection.execute("UPDATE $userTable SET is_enabled=true WHERE id=?", listOf(id.toString()))

    suspend fun findByCredentials(connection: SuspendingConnection, email: String, passwordHash: String): User =
        connection.select(
            "SELECT id, first_name, last_name, email, password_hash, registered_at, visited_at, role, is_enabled FROM $userTable WHERE is_enabled=true AND email=? AND password_hash=?",
            listOf(email, passwordHash),
            userMapper
        ) ?: throw UserNotFoundException("user not found by credentials")

    suspend fun findById(connection: SuspendingConnection, id: UUID, isEnabled: Boolean): User =
        connection.select(
            "SELECT id, first_name, last_name, email, password_hash, registered_at, visited_at, role, is_enabled FROM $userTable WHERE id=? AND is_enabled=?",
            listOf(id, isEnabled),
            userMapper
        ) ?: throw UserNotFoundException("user not found by id")

    suspend fun findByEmail(connection: SuspendingConnection, email: String, isEnabled: Boolean = true): User =
        connection.select(
            "SELECT id, first_name, last_name, email, password_hash, registered_at, visited_at, role, is_enabled FROM $userTable WHERE login_email=? AND is_enabled=?",
            listOf(email, isEnabled),
            userMapper
        ) ?: throw UserNotFoundException("unknown user")
}