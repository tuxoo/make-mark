package com.makemark.repository

import com.makemark.model.entity.Mark
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface MarkRepository : ReactiveMongoRepository<Mark, String> {

    fun findAllByUserIdAndYearAndMonth(userId: ObjectId, year: Int, month: Int): Flux<Mark>

    fun findAllByUserIdAndYearAndMonthAndDay(userId: ObjectId, year: Int, month: Int, day: Int): Flux<Mark>
}