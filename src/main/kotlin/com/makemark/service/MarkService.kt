package com.makemark.service

import com.makemark.repository.MarkRepository
import org.springframework.stereotype.Service

@Service
class MarkService(
    private val markRepository: MarkRepository
) {

//    suspend fun create(markFormDto: MarkFormDto): MarkSlimDto =
//        toMark(markFormDto).run {
//            markRepository.save(pool, this)
//        }.run { MarkSlimDto(
//            id = this.id!!,
//            title = this.title,
//            text = this.text,
//            createdAt = this.createdAt
//        ) }
//
//    suspend fun edit(id: Long, markFormDto: MarkFormDto): MarkSlimDto =
//        markRepository.update(pool, id, markFormDto).run {
//            MarkSlimDto(
//                id = this.id!!,
//                title = this.title,
//                text = this.text,
//                createdAt = this.createdAt
//            )
//        }
//
//    suspend fun getByDate(year: Int, month: Int, day: Int?): List<MarkSlimDto> =
//        markRepository.findByDate(pool, year, month, day)
//            .map {
//                MarkSlimDto(
//                    id = it.id!!,
//                    title = it.title,
//                    text = it.text,
//                    createdAt = it.createdAt
//                )
//            }
//
//    suspend fun delete(id: Long): Long =
//        markRepository.delete(pool, id)
//
//    private suspend fun toMark(markFormDto: MarkFormDto): Mark =
//        Instant.now().run {
//            Mark(
//                title = markFormDto.title,
//                text = markFormDto.text,
//                year = DateTimeUtil.getYear(this),
//                month = DateTimeUtil.getMonth(this),
//                day = DateTimeUtil.getDay(this),
//                createdAt = this,
//                userId = getCurrentUserDetails().getId()
//            )
//        }
}