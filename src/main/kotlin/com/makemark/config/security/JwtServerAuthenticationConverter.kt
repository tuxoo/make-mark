package com.makemark.config.security

import com.makemark.model.enums.Auth
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtServerAuthenticationConverter : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        return Mono.justOrEmpty(exchange.request.headers.getFirst(Auth.AUTHORIZATION.meaning))
            .filter { it.startsWith(Auth.BEARER.meaning) }
            .map { it.substring(Auth.BEARER.length) }
            .map { jwt -> BearerToken(jwt) }
    }

}