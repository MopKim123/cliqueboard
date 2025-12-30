package com.mhkim.cliqueboard.controller

import com.mhkim.cliqueboard.model.LoginRequest
import com.mhkim.cliqueboard.model.LoginResponse
import com.mhkim.cliqueboard.model.PasswordRequest
import com.mhkim.cliqueboard.model.User
import com.mhkim.cliqueboard.model.UserRequest
import com.mhkim.cliqueboard.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<User> =
        userService.getUserById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @GetMapping("/by-username/{username}")
    fun getByUsername(@PathVariable username: String): ResponseEntity<User> =
        userService.getUserByUsername(username)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @GetMapping
    fun listAll(): ResponseEntity<List<User>> =
        ResponseEntity.ok(userService.getAllUsers())

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody user: User): ResponseEntity<User> {
        return try {
            val updated = userService.updateUser(id, user)
            ResponseEntity.ok(updated)
        } catch (ex: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}/password")
    fun updatePassword(@PathVariable id: Long, @RequestBody req: PasswordRequest): ResponseEntity<User> {
        return try {
            val updated = userService.updatePassword(id, req.newPassword)
            ResponseEntity.ok(updated)
        } catch (ex: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        return if (userService.deleteUser(id)) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
    }
}

