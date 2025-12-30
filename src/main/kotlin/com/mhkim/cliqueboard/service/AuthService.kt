package com.mhkim.cliqueboard.service

import com.mhkim.cliqueboard.model.LoginResponse
import com.mhkim.cliqueboard.model.User
import com.mhkim.cliqueboard.model.UserRequest

interface AuthService {
    fun createUser(user: UserRequest)
    fun login(username: String, password: String): LoginResponse
}