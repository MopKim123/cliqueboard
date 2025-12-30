package com.mhkim.cliqueboard.controller

import com.mhkim.cliqueboard.model.LoginRequest
import com.mhkim.cliqueboard.model.LoginResponse
import com.mhkim.cliqueboard.model.PasswordRequest
import com.mhkim.cliqueboard.model.User
import com.mhkim.cliqueboard.model.UserRequest
import com.mhkim.cliqueboard.service.AuthService
import com.mhkim.cliqueboard.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody req: UserRequest) {
        authService.createUser(req)
    }

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<LoginResponse> =
         authService.login(req.username, req.password)
            .let { ResponseEntity.ok(it) }
}

