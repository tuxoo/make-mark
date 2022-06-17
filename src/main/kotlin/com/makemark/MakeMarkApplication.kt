package com.makemark

import com.makemark.config.property.ApplicationProperty
import com.makemark.config.property.CacheProperty
import com.makemark.config.property.PostgresProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(
    ApplicationProperty::class,
    PostgresProperty::class,
    CacheProperty::class
)
class MakeMarkApplication

fun main(args: Array<String>) {
    runApplication<MakeMarkApplication>(*args)
}
