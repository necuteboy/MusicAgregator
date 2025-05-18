package com.example.MusicAgregator.model

data class ResponseTopTracks(
    val page: Int,
    val pagesCount: Int,
    val trackIds: List<Int>,
    val tracksInfo: Map<String, TrackInfo>,
    val limit: Int
)

data class TrackInfo(
    val size: Double,
    val track: String,
    val bitrate: Int,
    val duration: String,
    val artistName: String,
    val playbackEnabled: Boolean,
    val downloadEnabled: Boolean,
    val imageJpg: String,
    val imageWebp: String,
    val explicit: Boolean,
    val artistId: Long,
    val isArtistForeignAgent: Boolean
)