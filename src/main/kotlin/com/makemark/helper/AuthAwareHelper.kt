package com.makemark.helper

import com.makemark.config.security.AppUserDetails
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.core.context.ReactiveSecurityContextHolder

object AuthAwareHelper {

    suspend fun getCurrentUserDetails(): AppUserDetails =
        ReactiveSecurityContextHolder.getContext()
            .map{
                it.authentication
            }.map {
                it.principal
            }.filter {
                AppUserDetails::class.isInstance(it)
            }
            .cast(AppUserDetails::class.java)
            .awaitSingleOrNull()
            ?: error("unauthorized user")

}