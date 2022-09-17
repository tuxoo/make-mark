package com.makemark.config.security

import com.makemark.service.UserService
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AppUserDetailsService(
    private val userService: UserService
) : ReactiveUserDetailsService {

    override fun findByUsername(login: String?): Mono<UserDetails> =
        mono {
            userService.getByEmailOrThrow(login!!)
                .run {
                    AppUserDetails.toUserDetails(this)
                }
        }

    fun findById(id: String): Mono<UserDetails> =
        mono {
            userService.getById(id)
                .run {
                    AppUserDetails.toUserDetails(this)
                }
        }
}