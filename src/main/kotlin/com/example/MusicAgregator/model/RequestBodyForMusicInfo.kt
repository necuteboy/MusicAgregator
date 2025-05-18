package com.example.MusicAgregator.model

data class RequestBodyForMusicInfo(
    val trackIds: List<String>,
    val subscription: Boolean
)