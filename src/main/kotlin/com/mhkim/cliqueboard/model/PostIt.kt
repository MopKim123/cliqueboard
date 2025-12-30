package com.mhkim.cliqueboard.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "post_its")
data class PostIt(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    val board: Board,

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: User? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    val message: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)