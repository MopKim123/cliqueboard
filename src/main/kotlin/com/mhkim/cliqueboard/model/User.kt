package com.mhkim.cliqueboard.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "first_name", nullable = false, length = 100)
    var firstName: String,

    @Column(name = "last_name", nullable = false, length = 100)
    var lastName: String,

    var birthday: LocalDate? = null,

    @Column(nullable = false, unique = true, length = 255)
    var email: String,

    @Column(nullable = false, unique = true, length = 50)
    var username: String,

    @Column(nullable = false, length = 255)
    var password: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToMany(mappedBy = "members")
    val boards: Set<Board> = emptySet()
)

// ========== DTOs ==========
data class UserDTO(
    var id: Long,
    var firstName: String,
    var lastName: String,
    var birthday: LocalDate?,
    var email: String,
    var username: String,
    var createdAt: LocalDateTime
)

data class LoginRequest(
    var username: String,
    var password: String
)

data class PasswordRequest(
    var newPassword: String
)

data class LoginResponse(
    var id: Long,
    var username: String,
    var token: String
)

data class UserRequest(
    var firstName: String,
    var lastName: String,
    var birthday: LocalDate?,
    var email: String,
    var username: String,
    var password: String
)

// ========== Mappers ==========

fun User.toDTO() = UserDTO(
    id = this.id,
    firstName = this.firstName,
    lastName = this.lastName,
    birthday = this.birthday,
    email = this.email,
    username = this.username,
    createdAt = this.createdAt
)

fun UserRequest.toEntity() = User(
    firstName = this.firstName,
    lastName = this.lastName,
    birthday = this.birthday,
    email = this.email,
    username = this.username,
    password = this.password
)

fun String.toLoginResponse(user: User) = LoginResponse(
    id = user.id,
    username = user.username,
    token = this
)
