package com.makemark.repository

import com.makemark.model.entity.Mark
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface MarkRepository : ReactiveMongoRepository<Mark, String> {

    fun findAllByYearAndMonth(year: Int, month: Int): Flux<Mark>

    fun findAllByYearAndMonthAndDay(year: Int, month: Int, day: Int): Flux<Mark>
}