package com.makemark.model.dto

import javax.validation.constraints.NotBlank

data class SignInDto(
    @NotBlank(message = "email is blank") val email: String,
    @NotBlank(message = "password is blank") val password: String
)
