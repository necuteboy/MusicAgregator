package com.example.MusicAgregator.model

import jakarta.persistence.*

@Entity
@Table(name = "music_jn")
data class MusicModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "name")
    val name: String,
    @Column(name = "author")
    val author: String,
    @Column(name = "genre")
    val genre: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val users: User
)