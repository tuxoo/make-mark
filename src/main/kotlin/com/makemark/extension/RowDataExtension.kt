package com.makemark.extension

import com.github.jasync.sql.db.RowData
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

fun RowData.getNonNullableString(column: String): String = getString(column) ?: error("failed to get column mean [$column]")

fun RowData.getUUID(column: String): UUID? = this[column] as UUID?

fun RowData.getNonNullableUUID(column: String): UUID = getUUID(column) ?: error("failed to get column mean [$column]")

fun RowData.getInstant(column: String, zoneOffset: ZoneOffset = ZoneOffset.UTC): Instant? =
    (this[column] as LocalDateTime?)?.toInstant(zoneOffset)

fun RowData.getNonNullableInstant(column: String, zoneOffset: ZoneOffset = ZoneOffset.UTC): Instant =
    getInstant(column, zoneOffset) ?: error("failed to get column mean [$column]")

fun RowData.getNonNullableBoolean(column: String): Boolean = getBoolean(column) ?: error("failed to get column mean [$column]")

fun RowData.getNonNullableLong(column: String): Long = getLong(column) ?: error("failed to get column mean [$column]")

fun RowData.getNonNullableInt(column: String): Int = getInt(column) ?: error("failed to get column mean [$column]")