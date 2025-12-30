package com.mhkim.cliqueboard.service.impl

import com.mhkim.cliqueboard.model.LoginResponse
import com.mhkim.cliqueboard.model.User
import com.mhkim.cliqueboard.model.UserRequest
import com.mhkim.cliqueboard.model.toEntity
import com.mhkim.cliqueboard.model.toLoginResponse
import com.mhkim.cliqueboard.repository.UserRepository
import com.mhkim.cliqueboard.service.AuthService
import com.mhkim.cliqueboard.service.UserService
import com.mhkim.cliqueboard.util.JwtUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil
) : AuthService {

    private val passwordEncoder = BCryptPasswordEncoder()

    @Transactional
    override fun createUser(user: UserRequest) {
        userRepository.findByUsername(user.username)?.let {
            throw IllegalArgumentException("username already in use")
        }
        userRepository.findByEmail(user.email)?.let {
            throw IllegalArgumentException("email already in use")
        }

        user.toEntity()
            .apply{ password = passwordEncoder.encode(user.password)!! }
            .let { userRepository.save(it) }
    }

    override fun login(username: String, password: String): LoginResponse {
        val user = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("Invalid credentials")

        if (!passwordEncoder.matches(password, user.password)) {
            throw IllegalArgumentException("Invalid credentials")
        }

        return jwtUtil.generateToken(user.username).toLoginResponse(user)
    }
}