package com.makemark.controller

import com.makemark.service.MarkService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/api/v1/items")
class ItemController(
    val markService: MarkService
) {

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get exist years", description = "This method gets years which contains marks")
    @GetMapping("/years")
    suspend fun getByYear(): MutableList<Int>? = markService.fetchYears()
}