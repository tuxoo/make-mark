package com.makemark.repository

import com.makemark.model.entity.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : ReactiveMongoRepository<User, String> {
    fun findByEmail(email: String): Mono<User>

    fun findByEmailAndPasswordHash(email: String, passwordHash: String): Mono<User>
}