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
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@ControllerAdvice
class ExceptionHandlerController {

    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault())
        private val log = getLogger<ExceptionHandlerController>()
    }

    @ExceptionHandler(GenericDatabaseException::class)
    fun handleDatabaseException(e: GenericDatabaseException): ResponseEntity<ErrorResponse> =
        with(e.errorMessage)
        {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    ErrorResponse(
                        message = this.message ?: "error",
                        errorTime = formatter.format(Instant.now())
                    )
                )
        }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> =
        with(e.javaClass.getAnnotation(ResponseStatus::class.java))
        {
            ResponseEntity.status(this?.code ?: HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    ErrorResponse(
                        message = e.message ?: "error",
                        errorTime = formatter.format(Instant.now())
                    )
                )
        }
}