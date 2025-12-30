package com.mhkim.cliqueboard.repository

import com.mhkim.cliqueboard.model.Board
import com.mhkim.cliqueboard.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : JpaRepository<Board, Long> {
    fun findByName(name: String): Board?
    fun findAllByMembersContains(member: User): List<Board>
}

