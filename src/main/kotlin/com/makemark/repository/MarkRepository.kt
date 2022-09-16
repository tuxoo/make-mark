package com.makemark.repository

import com.github.jasync.sql.db.ResultSet
import com.github.jasync.sql.db.SuspendingConnection
import com.makemark.extension.*
import com.makemark.model.dto.MarkFormDto
import com.makemark.model.entity.Mark
import com.makemark.model.exception.MarkNotFoundException
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class MarkRepository {

    companion object {
        const val markTable = "mark"
    }

    private val markMapper: (ResultSet) -> Mark = {
        Mark(
            id = it[0].getNonNullableLong("id"),
            title = it[0].getNonNullableString("title"),
            text = it[0].getNonNullableString("text"),
            year = it[0].getNonNullableInt("year"),
            month = it[0].getNonNullableInt("month"),
            day = it[0].getNonNullableInt("day"),
            createdAt = it[0].getNonNullableInstant("created_at"),
            userId = it[0].getNonNullableUUID("user_id")
        )
    }

    suspend fun save(connection: SuspendingConnection, mark: Mark): Mark =
        connection.execute(
            "INSERT INTO $markTable (title, text, year, month, day, created_at, user_id) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING *",
            listOf(
                mark.title,
                mark.text,
                mark.year,
                mark.month,
                mark.day,
                Timestamp.from(mark.createdAt),
                mark.userId
            )
        ).rows.run(markMapper)

    suspend fun update(connection: SuspendingConnection, id: Long, formDto: MarkFormDto): Mark =
        connection.execute(
            "UPDATE $markTable SET title=?, text=? WHERE id=? RETURNING *",
            listOf(formDto.title, formDto.text, id)
        ).rows.run(markMapper)

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

    suspend fun delete(connection: SuspendingConnection, id: Long): Long =
        connection.execute(
            "DELETE FROM $markTable WHERE id=? RETURNING id",
            listOf(id)
        ).rows.takeIf { it.size > 0 }
            .run {
                this?.get(0)?.getNonNullableLong("id") ?: throw MarkNotFoundException("Mark not found by id [${id}]")
            }
}