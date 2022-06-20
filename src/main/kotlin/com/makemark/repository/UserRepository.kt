package com.makemark.repository

import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.SuspendingConnection
import com.makemark.extension.*
import com.makemark.model.dto.UserDTO
import com.makemark.model.entity.User
import com.makemark.model.enums.Role
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.util.*

@Repository
class UserRepository {

    companion object {
        const val userTable = "\"user\""
    }

    private val userDTOMapper: (RowData) -> UserDTO = {
        val id = it.getNonNullableUUID("id")
        val name = it.getNonNullableString("name")
        val loginEmail = it.getNonNullableString("login_email")
        val registeredAt = it.getNonNullableInstant("registered_at")
        val visitedAt = it.getNonNullableInstant("visited_at")
        val role = it.getNonNullableString("role").run { Role.valueOf(this) }
        val isEnabled = it.getNonNullableBoolean("is_enabled")
        UserDTO(
            id = id,
            name = name,
            loginEmail = loginEmail,
            registeredAt = registeredAt,
            visitedAt = visitedAt,
            role = role,
            isEnabled = isEnabled
        )
    }

    suspend fun save(connection: SuspendingConnection, user: User): Unit =
        with(user) {
            connection.execute(
                "INSERT INTO $userTable (name, login_email, password_hash, registered_at, visited_at, role) VALUES (?, ?, ?, ?, ?, ?)",
                listOf(
                    name,
                    loginEmail,
                    passwordHash,
                    Timestamp.from(registeredAt),
                    Timestamp.from(visitedAt),
                    role.name
                )
            )
        }

    suspend fun updateUser(connection: SuspendingConnection, id: UUID) =
        connection.execute("UPDATE $userTable SET is_enabled=true WHERE id=?", listOf(id.toString()))

    suspend fun findByCredentials(connection: SuspendingConnection, email: String, passwordHash: String): UserDTO =
        connection.select(
            "SELECT id, name, login_email, registered_at, visited_at, role, is_enabled FROM $userTable WHERE is_enabled=true AND login_email=? AND password_hash=?",
            listOf(email, passwordHash),
            userDTOMapper
        ) ?: error("user not found by credentials")

    suspend fun findById(connection: SuspendingConnection, id: UUID, isEnabled: Boolean): UserDTO =
        connection.select(
            "SELECT id, name, login_email, registered_at, visited_at, role, is_enabled FROM $userTable WHERE id=? AND is_enabled=?",
            listOf(id, isEnabled),
            userDTOMapper
        ) ?: error("user not found by id")

    suspend fun findByEmail(connection: SuspendingConnection, email: String): UserDTO =
        connection.select(
            "SELECT id, name, login_email, registered_at, visited_at, role, is_enabled FROM $userTable WHERE is_enabled=true AND login_email=?",
            listOf(email),
            userDTOMapper
        ) ?: error("user not found by email")
}