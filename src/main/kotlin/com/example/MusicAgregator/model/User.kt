package com.example.MusicAgregator.model

import jakarta.persistence.*

@Entity
@Table(name = "user_jn")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "name")
    val name: String,
    @Column(name = "role")
    var role: String,
    @Column(name = "password")
    val password: String,
    @Column(name = "gender")
    val gender: String,
    @Column(name = "is_banned")
    var isBanned: Boolean? = false,
    @Column(name = "email")
    val email: String,
    @ManyToMany
    val musics: List<MusicModel>? = emptyList()
)