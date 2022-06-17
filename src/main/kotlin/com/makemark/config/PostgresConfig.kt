package com.makemark.config

import com.github.jasync.sql.db.ConnectionPoolConfiguration
import com.github.jasync.sql.db.SuspendingConnection
import com.github.jasync.sql.db.asSuspending
import com.github.jasync.sql.db.pool.ConnectionPool
import com.github.jasync.sql.db.pool.PoolConfiguration
import com.github.jasync.sql.db.postgresql.pool.PostgreSQLConnectionFactory
import com.makemark.config.property.PostgresProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit
import com.github.jasync.sql.db.Configuration as JasyncConfiguration

@Configuration(proxyBeanMethods = false)
class PostgresConfig(
    private val postgresProperty: PostgresProperty
) {

//    @Bean
//    @Primary
//    fun pool(properties: PostgresProperty): SuspendingConnection =
//        PostgreSQLConnectionBuilder.createConnectionPool(properties.url) {
//            username = properties.username
//            password = properties.password
//            maxActiveConnections = properties.maxActiveConnections
//            maxPendingQueries = properties.maxPendingQueries
//            ssl = SSLConfiguration(SSLConfiguration.Mode.Prefer)
//            maxConnectionTtl = properties.maxConnectionTtl
//            maxIdleTime = properties.maxIdleTime
//
//        }.asSuspending

    private val configuration = JasyncConfiguration(
        postgresProperty.username,
        postgresProperty.host,
        postgresProperty.port,
        postgresProperty.password,
        postgresProperty.db
    )

    private val poolConfiguration = ConnectionPoolConfiguration(
//        maxObjects = 100,
//        maxIdle = TimeUnit.MINUTES.toMillis(15),
//        maxQueueSize = 10_000,
//        validationInterval = TimeUnit.SECONDS.toMillis(30)
    )

    @Bean
    fun pool() : SuspendingConnection =
        ConnectionPool(factory = PostgreSQLConnectionFactory(configuration), configuration = poolConfiguration).asSuspending
}