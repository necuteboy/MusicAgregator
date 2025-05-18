package com.example.MusicAgregator.model

data class ResponseForControllerTopTrack(
    val tracks : List<Tracks>
) {
    data class Tracks(
        val trackId : String,
        val trackName : String,
        val artist : String
    )
}