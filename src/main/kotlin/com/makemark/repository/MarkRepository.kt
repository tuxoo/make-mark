package com.makemark.repository

import com.github.jasync.sql.db.SuspendingConnection
import com.makemark.extension.*
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

    suspend fun findByDate(connection: SuspendingConnection, year: Int, month: Int, day: Int?): List<Mark> =
        connection.selectList(
            "SELECT * FROM $markTable WHERE year=? AND month=?${if (day == null) "" else " AND day=?"}",
            if (day == null) listOf(year, month) else listOf(year, month, day)
        ) {
            Mark(
                id = it.getNonNullableLong("id"),
                title = it.getNonNullableString("title"),
                text = it.getNonNullableString("text"),
                year = it.getNonNullableInt("year"),
                month = it.getNonNullableInt("month"),
                day = it.getNonNullableInt("day"),
                createdAt = it.getNonNullableInstant("created_at"),
                userId = it.getNonNullableUUID("user_id")
            )
        }
}