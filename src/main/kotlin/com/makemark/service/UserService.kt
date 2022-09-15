package com.makemark.service

import com.github.benmanes.caffeine.cache.AsyncCache
import com.github.jasync.sql.db.SuspendingConnection
import com.makemark.config.security.AppUserDetails
import com.makemark.config.security.JwtProvider
import com.makemark.extension.getSuspending
import com.makemark.extension.putSuspending
import com.makemark.model.dto.LoginResponse
import com.makemark.model.dto.SignInDto
import com.makemark.model.dto.SignUpDto
import com.makemark.model.dto.UserDto
import com.makemark.model.entity.User
import com.makemark.repository.UserRepository
import com.makemark.util.HashUtils
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.security.Principal
import java.time.Instant
import java.util.*

@Service
class UserService(
    private val pool: SuspendingConnection,
    private val userCache: AsyncCache<UUID, User>,
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val sessionService: SessionService
) {

    suspend fun signUp(signUpDTO: SignUpDto): Unit =
        userRepository.save(
            pool, User(
                firstName = signUpDTO.firstName,
                lastName = signUpDTO.lastName,
                email = signUpDTO.email,
                passwordHash = HashUtils.hashSHA1(signUpDTO.password),
                registeredAt = Instant.now(),
                visitedAt = Instant.now()
            )
        )

    suspend fun signIn(signInDTO: SignInDto): LoginResponse =
        userRepository.findByCredentials(
            connection = pool,
            email = signInDTO.email,
            passwordHash = HashUtils.hashSHA1(signInDTO.password)
        ).run {
            userCache.putSuspending(this.id, this)
            val refreshToken = sessionService.create(this)
            LoginResponse(
                accessToken = jwtProvider.generateToken(this.id.toString()).value,
                refreshToken = refreshToken,
                user = UserDto(
                    firstName = this.firstName,
                    lastName = this.lastName,
                    email = this.email,
                    registeredAt = this.registeredAt
                )
            )
        }

    suspend fun getUserProfile(principal: Principal): User {
        val user = (principal as UsernamePasswordAuthenticationToken).principal as AppUserDetails
        return getById(user.getId())
    }

    suspend fun getById(id: UUID, isEnabled: Boolean = true): User =
        userCache.getSuspending(id) {
            userRepository.findById(pool, id, isEnabled)
        }

    suspend fun getByEmail(email: String): User =
        userRepository.findByEmail(pool, email)
}