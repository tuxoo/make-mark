package com.makemark.model.dto

import javax.validation.constraints.NotBlank

data class SignUpDto(
    @NotBlank(message = "firstname is blank") val firstName: String,
    @NotBlank(message = "lastName is blank") val lastName: String,
    @NotBlank(message = "email is blank") val email: String,
    @NotBlank(message = "password is blank") val password: String
)
