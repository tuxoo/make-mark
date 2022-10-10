package com.makemark.config

import com.makemark.config.security.JwtAuthenticationManager
import com.makemark.config.security.JwtServerAuthenticationConverter
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import reactor.core.publisher.Mono

@EnableWebFluxSecurity
class SecurityConfig {

    val permittedUris = listOf(
        "/api/v1/users/sign-up",
        "/api/v1/users/sign-in",
        "/api/v1/users/token/refresh/**",
        "/actuator/**",
        "**/swagger-ui/**",
        "**/v3/api-docs/**",
        "/swagger-ui/**",
    )

    @Bean
    fun springSecurityFilterChain(
        converter: JwtServerAuthenticationConverter,
        http: ServerHttpSecurity,
        authManager: JwtAuthenticationManager
    ): SecurityWebFilterChain {

        val filter = AuthenticationWebFilter(authManager)
        filter.setServerAuthenticationConverter(converter)

        http
            .exceptionHandling()
            .authenticationEntryPoint { exchange, _ ->
                Mono.fromRunnable {
                    exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                    exchange.response.headers.set(HttpHeaders.WWW_AUTHENTICATE, "Bearer")
                }
            }
            .and()
            .authorizeExchange()
//            .pathMatchers(
//                HttpMethod.POST,
//                "/api/v1/users/sign-up",
//                "/api/v1/users/sign-in",
//                "/actuator/**"
//            )
//            .permitAll()
//            .pathMatchers(
//                HttpMethod.GET,
//                "/actuator/**",
//                "/swagger-ui/**",
//                "**/v3/api-docs/**",
//                "/swagger-ui.html")
//            .permitAll()
            .pathMatchers(*permittedUris.toTypedArray())
            .permitAll()
            .anyExchange().authenticated()
            .and()
            .addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION)
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()

        return http.build()
    }
}