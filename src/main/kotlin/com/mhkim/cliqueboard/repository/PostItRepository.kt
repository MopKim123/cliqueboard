package com.mhkim.cliqueboard.repository

import com.mhkim.cliqueboard.model.PostIt
import com.mhkim.cliqueboard.model.Board
import com.mhkim.cliqueboard.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostItRepository : JpaRepository<PostIt, Long> {
    fun findAllByBoard(board: Board): List<PostIt>
    fun findAllByAuthor(author: User): List<PostIt>
}

