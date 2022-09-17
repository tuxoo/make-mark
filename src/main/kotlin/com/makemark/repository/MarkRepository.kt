package com.makemark.repository

import com.github.jasync.sql.db.SuspendingConnection
import com.makemark.extension.*
import com.makemark.model.dto.MarkFormDto
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

    suspend fun update(connection: SuspendingConnection, id: Long, formDto: MarkFormDto): Mark =
        connection.execute(
            "UPDATE $markTable SET title=?, text=? WHERE id=? RETURNING *",
            listOf(formDto.title, formDto.text, id)
        ).rows.run {
            Mark(
                id = this[0].getNonNullableLong("id"),
                title = this[0].getNonNullableString("title"),
                text = this[0].getNonNullableString("text"),
                year = this[0].getNonNullableInt("year"),
                month = this[0].getNonNullableInt("month"),
                day = this[0].getNonNullableInt("day"),
                createdAt = this[0].getNonNullableInstant("created_at"),
                userId = this[0].getNonNullableString("user_id")
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
                userId = it.getNonNullableString("user_id")
            )
        }

    suspend fun delete(connection: SuspendingConnection, id: Long): Unit =
        with(id) {
            connection.execute(
                "DELETE FROM $markTable WHERE id=?",
                listOf(this)
            )
        }
}