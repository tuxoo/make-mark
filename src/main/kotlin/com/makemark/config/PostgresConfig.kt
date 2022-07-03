package com.makemark.config

import com.github.jasync.sql.db.ConnectionPoolConfiguration
import com.github.jasync.sql.db.SuspendingConnection
import com.github.jasync.sql.db.asSuspending
import com.github.jasync.sql.db.pool.ConnectionPool
import com.github.jasync.sql.db.postgresql.pool.PostgreSQLConnectionFactory
import com.makemark.config.property.PostgresProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.github.jasync.sql.db.Configuration as JasyncConfiguration

@Configuration
class PostgresConfig(
    private val postgresProperty: PostgresProperty
) {

    private val configuration = JasyncConfiguration(
        postgresProperty.username,
        postgresProperty.host,
        postgresProperty.port,
        postgresProperty.password,
        postgresProperty.db
    )

    // maxActiveConnections = 25
    // maxConnectionTtl = 120000
    // maxIdleTime = 300000
    private val poolConfiguration = ConnectionPoolConfiguration(
        maxActiveConnections = postgresProperty.maxActiveConnections,
        maxPendingQueries = postgresProperty.maxPendingQueries,
        maxConnectionTtl = postgresProperty.maxConnectionTtl,
        maxIdleTime = postgresProperty.maxIdleTime
    )

    @Bean
    fun pool(): SuspendingConnection =
        ConnectionPool(
            factory = PostgreSQLConnectionFactory(configuration),
            configuration = poolConfiguration
        ).asSuspending
}