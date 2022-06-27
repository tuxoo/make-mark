package com.makemark.controller

import com.makemark.model.dto.*
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

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun signUp(@RequestBody signUpDTO: SignUpDTO): Unit =
        userService.signUp(signUpDTO)

    @PostMapping("/verify")
    suspend fun verifyUser(@RequestBody verifyDTO: VerifyDTO): Unit =
        userService.verifyUser(verifyDTO)

    @PostMapping("/sign-in")
    suspend fun signIn(@RequestBody signInDTO: SignInDTO): TokenDTO =
        userService.signIn(signInDTO)

    @GetMapping("/profile")
    suspend fun getUserProfile(@AuthenticationPrincipal principal: Principal): UserDTO =
        userService.getUserProfile(principal)

    @GetMapping("/{email}")
    suspend fun getUserByEmail(@PathVariable email: String): UserDTO =
        userService.getByLoginEmail(email)
}