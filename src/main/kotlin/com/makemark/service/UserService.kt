package com.makemark.service

import com.github.benmanes.caffeine.cache.AsyncCache
import com.github.jasync.sql.db.SuspendingConnection
import com.makemark.config.security.AppUserDetails
import com.makemark.config.security.JwtProvider
import com.makemark.extension.getSuspending
import com.makemark.extension.putSuspending
import com.makemark.model.dto.SignInDTO
import com.makemark.model.dto.SignUpDTO
import com.makemark.model.dto.TokenDTO
import com.makemark.model.dto.UserDTO
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
    private val userCache: AsyncCache<UUID, UserDTO>,
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider
) {

    suspend fun signUp(signUpDTO: SignUpDTO): Unit =
        userRepository.save(
            pool, User(
                name = signUpDTO.name,
                loginEmail = signUpDTO.email,
                passwordHash = HashUtils.hashSHA1(signUpDTO.password),
                registeredAt = Instant.now(),
                visitedAt = Instant.now()
            )
        )

    suspend fun signIn(signInDTO: SignInDTO): TokenDTO =
        userRepository.findByCredentials(
            connection = pool,
            email = signInDTO.email,
            passwordHash = HashUtils.hashSHA1(signInDTO.password)
        ).run {
            userCache.putSuspending(this.id, this)
            TokenDTO(jwtProvider.generateToken(this.id.toString()).value)
        }

    suspend fun getUserProfile(principal: Principal): UserDTO {
        val user = (principal as UsernamePasswordAuthenticationToken).principal as AppUserDetails
        return getById(user.getId())
    }


    suspend fun getById(id: UUID, isEnabled: Boolean = true): UserDTO =
        userCache.getSuspending(id) {
            userRepository.findById(pool, id, isEnabled)
        }

    suspend fun getByEmail(email: String): UserDTO =
        userRepository.findByEmail(pool, email)
}