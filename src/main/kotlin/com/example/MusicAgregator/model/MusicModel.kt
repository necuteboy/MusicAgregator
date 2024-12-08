package com.example.MusicAgregator.model

import jakarta.persistence.*

@Entity
@Table(name = "music_jn")
data class MusicModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "name")
    val name: String,
    @Column(name = "author")
    val author: String,
    @Column(name = "genre")
    val genre: String,
    val musicFileExtract: Byte,
    @ManyToMany
    val users: List<User>
)