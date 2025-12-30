package com.mhkim.cliqueboard.repository

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import com.mhkim.cliqueboard.model.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?
}



