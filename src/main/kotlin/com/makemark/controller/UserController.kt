package com.makemark.controller

import com.makemark.model.dto.SignInDTO
import com.makemark.model.dto.SignUpDTO
import com.makemark.model.dto.LoginResponse
import com.makemark.model.entity.User
import com.makemark.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    suspend fun signUp(@RequestBody signUpDTO: SignUpDTO): Unit =
        userService.signUp(signUpDTO)

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/sign-in")
    suspend fun signIn(@RequestBody signInDTO: SignInDTO): LoginResponse =
        userService.signIn(signInDTO)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/profile")
    suspend fun getUserProfile(@AuthenticationPrincipal principal: Principal): User =
        userService.getUserProfile(principal)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{email}")
    suspend fun getUserByEmail(@PathVariable email: String): User =
        userService.getByEmail(email)
}