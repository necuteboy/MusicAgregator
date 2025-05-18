package com.example.MusicAgregator.model

class ResponseForMusicInfo(
    val tracks: List<Track>
) {
    data class Track(
        val id : Long,
        val streaming: String,
        val download: String
    )
}