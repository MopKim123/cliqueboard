package com.mhkim.cliqueboard.service

import com.mhkim.cliqueboard.model.LoginResponse
import com.mhkim.cliqueboard.model.User
import com.mhkim.cliqueboard.model.UserRequest

interface UserService {
    fun getUserById(id: Long): User?
    fun getUserByUsername(username: String): User?
    fun getUserByEmail(email: String): User?
    fun getAllUsers(): List<User>
    fun updateUser(id: Long, user: User): User
    fun deleteUser(id: Long): Boolean
    fun updatePassword(id: Long, newPassword: String): User?
}