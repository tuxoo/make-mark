package com.makemark.repository

import com.github.jasync.sql.db.SuspendingConnection
import com.makemark.extension.execute
import com.makemark.model.entity.Mark
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class MarkRepository {

    companion object {
        const val markTable = "mark"
    }

    suspend fun save(connection: SuspendingConnection, mark: Mark): Unit =
        with(mark) {
            connection.execute(
                "INSERT INTO $markTable (title, text, year, month, day, created_at, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                listOf(
                    title,
                    text,
                    year,
                    month,
                    day,
                    Timestamp.from(createdAt),
                    userId
                )
            )
        }
}