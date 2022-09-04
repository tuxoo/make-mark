package com.makemark.config

import com.makemark.config.property.ApplicationProperty
import com.makemark.model.enums.Auth.BEARER
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig(
    private val property: ApplicationProperty,
) {

    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .components(
            Components()
                .addSecuritySchemes(
                    BEARER.value.trim(),
                    SecurityScheme().type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER.value.trim())
                        .bearerFormat(BEARER.value.trim())
                )
        )
        .info(
            Info()
                .title("MakeMark Application")
                .description("Helper application for marking some important notices or not")
                .contact(Contact().name("krivtsov.eugene@gmail.com"))
                .version("0.0.1")
        )
        .run {
            if (property.url.isNotBlank()) {
                this.servers(listOf(Server().url(property.url + property.apiPath)))
            }
            this
        }

    @Bean
    fun publicApi(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("API")
        .pathsToMatch("/api/v1/**")
        .build()
}