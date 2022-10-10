package com.makemark.repository

import com.makemark.model.entity.Session
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface SessionRepository : ReactiveMongoRepository<Session, String> {

    fun findAllByUserId(userId: String): Flux<Session>

    fun findByRefreshToken(token: UUID): Mono<Session>

    fun deleteByRefreshToken(token: UUID)
}