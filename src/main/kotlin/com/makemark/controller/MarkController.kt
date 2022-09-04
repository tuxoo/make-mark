package com.makemark.controller

import com.makemark.model.dto.MarkFormDto
import com.makemark.model.dto.MarkSlimDto
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    suspend fun getByYear(
        @RequestParam("year", required = true) year: Int,
        @RequestParam("month", required = true) month: Int,
        @RequestParam("day", required = false) day: Int?
    ): List<MarkSlimDto> = markService.getByDate(year, month, day)

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    suspend fun edit(@PathVariable("id", required = true) id: Long, @RequestBody formDto: MarkFormDto): MarkSlimDto =
        markService.edit(id, formDto)

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable("id", required = true) id: Long): Unit =
        markService.delete(id)
}