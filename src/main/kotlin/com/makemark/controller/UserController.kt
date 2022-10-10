package com.makemark.controller

import com.makemark.model.dto.*
import com.makemark.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Sign up", description = "This method registers a new user")
    @PostMapping("/sign-up")
    suspend fun signUp(@RequestBody signUpDTO: SignUpDto): Unit =
        userService.signUp(signUpDTO)

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Sign in", description = "This method authenticates the user")
    @PostMapping("/sign-in")
    suspend fun signIn(@RequestBody signInDTO: SignInDto): LoginResponse =
        userService.signIn(signInDTO)

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Refresh token", description = "This method refreshes token if it exists")
    @PutMapping("/token/refresh")
    suspend fun refresh(@RequestBody tokenWrapper: TokenWrapper): TokenWrapper =
        userService.refreshToken(tokenWrapper.refreshToken)

    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Logout", description = "This method deletes the current user's session")
    @PostMapping("/logout")
    suspend fun logout(@RequestBody tokenWrapper: TokenWrapper): Unit =
        userService.logout(tokenWrapper.refreshToken)

    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Get user profile", description = "This method gets the user profile")
    @GetMapping("/profile")
    suspend fun getUserProfile(): UserDto =
        userService.getUserProfile()

    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Get user by email", description = "This method gets the user by the email")
    @GetMapping("/email/{email}")
    suspend fun getUserByEmail(@PathVariable email: String): UserDto =
        userService.getByEmail(email)
}