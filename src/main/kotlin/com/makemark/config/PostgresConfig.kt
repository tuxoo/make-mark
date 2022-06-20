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

    private val poolConfiguration = ConnectionPoolConfiguration(
        maxActiveConnections = postgresProperty.maxActiveConnections,
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