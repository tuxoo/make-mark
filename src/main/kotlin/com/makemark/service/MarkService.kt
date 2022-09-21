package com.makemark.service

import com.makemark.helper.AuthAwareHelper.getCurrentUserDetails
import com.makemark.model.dto.MarkFormDto
import com.makemark.model.dto.MarkSlimDto
import com.makemark.model.entity.Mark
import com.makemark.model.exception.MarkNotFoundException
import com.makemark.repository.MarkRepository
import com.makemark.util.DateTimeUtil
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactor.awaitSingle
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant

@Service
class MarkService(
    private val markRepository: MarkRepository,
    private val mongoTemplate: ReactiveMongoTemplate,
) {

    suspend fun create(markFormDto: MarkFormDto): MarkSlimDto =
        toMark(markFormDto).run {
            markRepository.save(this).awaitSingle()
        }.run {
            MarkSlimDto(
                id = this.id.toString(),
                title = this.title,
                text = this.text,
                createdAt = this.createdAt
            )
        }

    suspend fun edit(id: String, markFormDto: MarkFormDto): MarkSlimDto =
        getByIdOrThrow(id).run {
            markRepository.save(
                Mark(
                    id = this.id,
                    title = markFormDto.title,
                    text = markFormDto.text,
                    year = year,
                    month = month,
                    day = day,
                    createdAt = createdAt,
                    userId = userId
                )
            ).awaitSingle()
        }.run {
            MarkSlimDto(
                id = this.id.toString(),
                title = title,
                text = text,
                createdAt = createdAt
            )
        }

    suspend fun getByIdOrThrow(id: String): Mark =
        markRepository.findById(id)
            .switchIfEmpty(Mono.error(MarkNotFoundException("Mark not found by id [$id]")))
            .awaitSingle()

    suspend fun getDaily(year: Int, month: Int, day: Int): List<MarkSlimDto> =
        with(getCurrentUserDetails().getId()) {
            markRepository.findAllByUserIdAndYearAndMonthAndDay(ObjectId(this), year, month, day)
        }.collectList()
            .awaitLast()
            .map {
                MarkSlimDto(
                    id = it.id.toString(),
                    title = it.title,
                    text = it.text,
                    createdAt = it.createdAt
                )
            }

    suspend fun getMonthly(year: Int, month: Int, pageable: Pageable): Page<MarkSlimDto> =
        with(getCurrentUserDetails().getId()) {
            markRepository.findAllByUserIdAndYearAndMonth(ObjectId(this), year, month, pageable)
        }.collectList()
            .zipWith(markRepository.count())
            .map { m -> PageImpl(m.t1, pageable, m.t2) }
            .awaitLast()
            .map {
                MarkSlimDto(
                    id = it.id.toString(),
                    title = it.title,
                    text = it.text,
                    createdAt = it.createdAt
                )
            }

    suspend fun delete(id: String): String =
        getByIdOrThrow(id).run {
            markRepository.delete(this).block()
            id
        }

    suspend fun fetchYears(): MutableList<Int>? =
        mongoTemplate.query<Mark>().distinct("year").`as`(Int::class.java).all().collectList().awaitLast()

    private suspend fun toMark(markFormDto: MarkFormDto): Mark =
        Instant.now().run {
            Mark(
                title = markFormDto.title,
                text = markFormDto.text,
                year = DateTimeUtil.getYear(this),
                month = DateTimeUtil.getMonth(this),
                day = DateTimeUtil.getDay(this),
                createdAt = this,
                userId = ObjectId(getCurrentUserDetails().getId())
            )
        }
}