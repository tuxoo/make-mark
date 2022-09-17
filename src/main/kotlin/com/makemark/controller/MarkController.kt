package com.makemark.controller

import com.makemark.model.dto.MarkFormDto
import com.makemark.model.dto.MarkSlimDto
import com.makemark.service.MarkService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/api/v1/marks")
class MarkController(
    val markService: MarkService
) {

//    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Create mark", description = "This method creates a mark")
//    @PostMapping
//    suspend fun add(@RequestBody formDto: MarkFormDto): MarkSlimDto = markService.create(formDto)
//
//    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Get marks list", description = "This method gets the mark list")
//    @GetMapping
//    suspend fun getByYear(
//        @RequestParam("year", required = true) year: Int,
//        @RequestParam("month", required = true) month: Int,
//        @RequestParam("day", required = false) day: Int?
//    ): List<MarkSlimDto> = markService.getByDate(year, month, day)
//
//    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Edit mark", description = "This method edits the mark by the id")
//    @PatchMapping("/{id}")
//    suspend fun edit(@PathVariable("id", required = true) id: Long, @RequestBody formDto: MarkFormDto): MarkSlimDto =
//        markService.edit(id, formDto)
//
//    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Delete mark", description = "This method deletes the mark by the id")
//    @DeleteMapping("/{id}")
//    suspend fun delete(@PathVariable("id", required = true) id: Long): Long =
//        markService.delete(id)
}