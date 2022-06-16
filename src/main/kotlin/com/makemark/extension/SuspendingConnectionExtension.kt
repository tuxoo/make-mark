package com.makemark.extension

import com.github.jasync.sql.db.QueryResult
import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.SuspendingConnection

suspend fun SuspendingConnection.execute(
    query: String,
    values: List<Any?>
): QueryResult = try {
    sendPreparedStatement(query, values)
} catch (e: Exception) {
    throw e
}

suspend fun <T> SuspendingConnection.selectList(
    query: String,
    values: List<Any>,
    mapper: (RowData) -> T
): List<T> = try {
    execute(query, values).rows.map { mapper(it) }
} catch (e: Exception) {
    throw e
}

suspend fun <T> SuspendingConnection.select(
    query: String,
    values: List<Any>,
    mapper: (RowData) -> T
): T? = selectList(query, values, mapper).firstOrNull()