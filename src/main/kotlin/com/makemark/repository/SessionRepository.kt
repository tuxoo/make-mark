package com.makemark.repository

import com.makemark.model.entity.Session
import com.makemark.model.entity.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface SessionRepository : ReactiveMongoRepository<Session, String> {

    fun findAllByUser(user: User): Flux<Session>
}