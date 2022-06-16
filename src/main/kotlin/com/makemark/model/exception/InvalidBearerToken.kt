package com.makemark.model.exception

import org.springframework.security.core.AuthenticationException

class InvalidBearerToken(message: String?) : AuthenticationException(message)

