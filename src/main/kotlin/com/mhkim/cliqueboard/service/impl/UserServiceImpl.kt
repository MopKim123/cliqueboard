package com.mhkim.cliqueboard.service.impl

import com.mhkim.cliqueboard.model.LoginResponse
import com.mhkim.cliqueboard.model.User
import com.mhkim.cliqueboard.model.UserRequest
import com.mhkim.cliqueboard.model.toEntity
import com.mhkim.cliqueboard.model.toLoginResponse
import com.mhkim.cliqueboard.repository.UserRepository
import com.mhkim.cliqueboard.service.UserService
import com.mhkim.cliqueboard.util.JwtUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil
) : UserService {

    private val passwordEncoder = BCryptPasswordEncoder()

    override fun getUserById(id: Long): User? =
        userRepository.findById(id).orElse(null)

    override fun getUserByUsername(username: String): User? =
        userRepository.findByUsername(username)

    override fun getUserByEmail(email: String): User? =
        userRepository.findByEmail(email)

    override fun getAllUsers(): List<User> =
        userRepository.findAll()

    @Transactional
    override fun updateUser(id: Long, user: User) =
        userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("User not found: $id") }
            .apply {
                firstName = user.firstName
                lastName = user.lastName
                birthday = user.birthday
                email = user.email
                username = user.username
            }
            .let( userRepository::save )

    @Transactional
    override fun updatePassword(id: Long, newPassword: String) =
        userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("User not found: $id") }
            .apply {
                password = passwordEncoder.encode(newPassword)!!
            }
            .let ( userRepository::save )


    @Transactional
    override fun deleteUser(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}