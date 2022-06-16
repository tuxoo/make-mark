package com.makemark.config

import com.github.jasync.sql.db.SSLConfiguration
import com.github.jasync.sql.db.SuspendingConnection
import com.github.jasync.sql.db.asSuspending
import com.github.jasync.sql.db.postgresql.PostgreSQLConnectionBuilder
import com.makemark.config.property.PostgresProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration(proxyBeanMethods = false)
class PostgresConfig {

    @Bean
    @Primary
    fun pool(properties: PostgresProperty): SuspendingConnection =
        PostgreSQLConnectionBuilder.createConnectionPool(properties.url) {
            username = properties.username
            password = properties.password
            maxActiveConnections = properties.maxActiveConnections
            maxPendingQueries = properties.maxPendingQueries
            ssl = SSLConfiguration(SSLConfiguration.Mode.Prefer)
            maxConnectionTtl
        }.asSuspending
}