package com.makemark.controller

import com.makemark.model.dto.MarkFormDto
import com.makemark.service.MarkService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/marks")
class MarkController(
    val markService: MarkService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    suspend fun add(@RequestBody formDto: MarkFormDto): Unit = markService.create(formDto)
}