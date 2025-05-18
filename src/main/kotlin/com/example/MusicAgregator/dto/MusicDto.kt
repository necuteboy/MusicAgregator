package com.example.MusicAgregator.dto


data class MusicDto(
    val id: Long? = null,

    val name: String,

    val author: String,

    val genre: String,

    val user: Long
)