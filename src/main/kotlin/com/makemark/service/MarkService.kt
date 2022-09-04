package com.makemark.service

import com.github.jasync.sql.db.SuspendingConnection
import com.makemark.helper.AuthAwareHelper.getCurrentUserDetails
import com.makemark.model.dto.MarkFormDto
import com.makemark.model.entity.Mark
import com.makemark.repository.MarkRepository
import com.makemark.util.DateTimeUtil
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class MarkService(
    private val pool: SuspendingConnection,
    val markRepository: MarkRepository
) {

    suspend fun create(markFormDto: MarkFormDto): Unit {
        toMark(markFormDto).run {
            markRepository.save(pool, this)
        }
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