package com.makemark.advice

import com.github.jasync.sql.db.postgresql.exceptions.GenericDatabaseException
import com.makemark.extension.getLogger
import com.makemark.model.error.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.Instant

@ControllerAdvice
class ExceptionHandlerController {

    companion object {
        private val log = getLogger<ExceptionHandlerController>()
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(GenericDatabaseException::class)
    fun handleDatabaseException(e: GenericDatabaseException): ResponseEntity<ErrorResponse> =
        with(e.errorMessage)
        {
            log.error("Handle exception [{}]", e.message ?: "error without description")
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    ErrorResponse(
                        message = this.message ?: "Generic Database error",
                        errorTime = Instant.now()
                    )
                )
        }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> =
        with(e.javaClass.getAnnotation(ResponseStatus::class.java))
        {
            log.error("Handle exception [{}]", e.message ?: "error without description")
            ResponseEntity.status(this?.value ?: HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    ErrorResponse(
                        message = e.message ?: "error",
                        errorTime = Instant.now()
                    )
                )
        }
}