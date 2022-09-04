package com.makemark.controller

import com.makemark.model.dto.LoginResponse
import com.makemark.model.dto.SignInDto
import com.makemark.model.dto.SignUpDto
import com.makemark.model.entity.User
import com.makemark.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Sign up", description = "This method registers a new user")
    @PostMapping("/sign-up")
    suspend fun signUp(
        @RequestBody signUpDTO: SignUpDto): Unit =
        userService.signUp(signUpDTO)

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Sign in", description = "This method authenticates the user")
    @PostMapping("/sign-in")
    suspend fun signIn(@RequestBody signInDTO: SignInDto): LoginResponse =
        userService.signIn(signInDTO)

    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Get user profile", description = "This method gets the user profile")
    @GetMapping("/profile")
    suspend fun getUserProfile(@AuthenticationPrincipal principal: Principal): User =
        userService.getUserProfile(principal)

    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Get user by email", description = "This method gets the user by the email")
    @GetMapping("/{email}")
    suspend fun getUserByEmail(@PathVariable email: String): User =
        userService.getByEmail(email)
}