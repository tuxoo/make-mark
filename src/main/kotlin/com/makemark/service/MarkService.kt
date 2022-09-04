package com.makemark.service

import com.github.jasync.sql.db.SuspendingConnection
import com.makemark.helper.AuthAwareHelper.getCurrentUserDetails
import com.makemark.model.dto.MarkFormDto
import com.makemark.model.dto.MarkSlimDto
import com.makemark.model.entity.Mark
import com.makemark.repository.MarkRepository
import com.makemark.util.DateTimeUtil
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class MarkService(
    private val pool: SuspendingConnection,
    private val markRepository: MarkRepository
) {

    suspend fun create(markFormDto: MarkFormDto): Unit =
        toMark(markFormDto).run {
            markRepository.save(pool, this)
        }

    suspend fun getByDate(year: Int, month: Int, day: Int?): List<MarkSlimDto> =
        markRepository.findByDate(pool, year, month, day)
            .map {
                MarkSlimDto(
                    id = it.id!!,
                    title = it.title,
                    text = it.text,
                    createdAt = it.createdAt
                )
            }

    private suspend fun toMark(markFormDto: MarkFormDto): Mark =
        Instant.now().run {
            Mark(
                title = markFormDto.title,
                text = markFormDto.text,
                year = DateTimeUtil.getYear(this),
                month = DateTimeUtil.getMonth(this),
                day = DateTimeUtil.getDay(this),
                createdAt = this,
                userId = getCurrentUserDetails().getId()
            )
        }
}