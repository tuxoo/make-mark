package com.makemark.service

import com.github.benmanes.caffeine.cache.AsyncCache
import com.makemark.config.security.JwtProvider
import com.makemark.extension.getSuspending
import com.makemark.extension.putSuspending
import com.makemark.helper.AuthAwareHelper
import com.makemark.model.dto.LoginResponse
import com.makemark.model.dto.SignInDto
import com.makemark.model.dto.SignUpDto
import com.makemark.model.dto.UserDto
import com.makemark.model.entity.User
import com.makemark.model.exception.UserNotFoundException
import com.makemark.repository.UserRepository
import com.makemark.util.HashUtils
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
    private val userCache: AsyncCache<String, User>,
    private val jwtProvider: JwtProvider,
    private val sessionService: SessionService,
    private val userRepository: UserRepository
) {

    suspend fun signUp(signUpDTO: SignUpDto): Unit =
        with(signUpDTO) {
            userRepository.save(
                User(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    passwordHash = HashUtils.hashSHA1(password)
                )
            ).awaitSingle()
        }

    suspend fun signIn(signInDTO: SignInDto): LoginResponse =
        getByCredentials(signInDTO.email, HashUtils.hashSHA1(signInDTO.password))
            .run {
                userCache.putSuspending(this.id.toString(), this)
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

    suspend fun getByCredentials(email: String, passwordHash: String): User =
        userRepository.findByEmailAndPasswordHash(
            email,
            passwordHash
        ).switchIfEmpty(Mono.error(UserNotFoundException("User not found by credentials")))
            .awaitSingle()

    suspend fun getUserProfile(): UserDto =
        with(AuthAwareHelper.getCurrentUserDetails()) {
            getById(getId())
        }.run {
            UserDto(
                firstName = firstName,
                lastName = lastName,
                email = email,
                registeredAt = registeredAt
            )
        }

    suspend fun getById(id: String): User =
        userCache.getSuspending(id) {
            userRepository.findById(id)
                .switchIfEmpty(Mono.error(UserNotFoundException("User not found by id [$id]")))
                .awaitSingle()
        }

    suspend fun getByEmailOrThrow(email: String): User =
        userRepository.findByEmail(email)
            .switchIfEmpty(Mono.error(UserNotFoundException("User not found by credentials")))
            .awaitSingle()

    suspend fun getByEmail(email: String): UserDto =
        getByEmailOrThrow(email)
            .run {
                UserDto(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    registeredAt = registeredAt
                )
            }
}