package com.example.MusicAgregator.dto

import com.example.MusicAgregator.model.User

data class MusicDto(
    val id: Long,
    val name: String,
    val author: String,
    val genre: String,
    val musicFileExtract: Byte,
    val users: List<User>
)