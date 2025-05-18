package com.example.MusicAgregator.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseMusicSetsModel(
    @JsonProperty("musicsets") val musicsets: MusicSets,
    @JsonProperty("onPage") val onPage: OnPage?,
    @JsonProperty("tracks") val tracks: Tracks
) {
    data class MusicSets(
        @JsonProperty("info") val info: Map<String, MusicSetInfo>
    )

    data class OnPage(
        @JsonProperty("page") val page: Int,
        @JsonProperty("totalCount") val totalCount: Int,
        @JsonProperty("pagesCount") val pagesCount: Int,
        @JsonProperty("list") val list: List<String>,
        @JsonProperty("type") val type: String,
        @JsonProperty("typeId") val typeId: String,
        @JsonProperty("params") val params: Map<String, Any>?,
        @JsonProperty("limit") val limit: Int?,
        @JsonProperty("categoryDescription") val categoryDescription: String?
    )

    data class Tracks(
        @JsonProperty("tracksInfo") val tracksInfo: Map<String, TrackInfo>,
        @JsonProperty("trackIds") val trackIds: List<Long>
    )

    data class MusicSetInfo(
        @JsonProperty("id") val id: Long,
        @JsonProperty("title") val title: String,
        @JsonProperty("url") val url: String,
        @JsonProperty("description") val description: String?,
        @JsonProperty("category") val category: String,
        @JsonProperty("presentationDate") val presentationDate: Long,
        @JsonProperty("trackCount") val trackCount: Int,
        @JsonProperty("square200") val square200: String?,
        @JsonProperty("square200Webp") val square200Webp: String?,
        @JsonProperty("top305") val top305: String?,
        @JsonProperty("top305Webp") val top305Webp: String?,
        @JsonProperty("size") val size: Double
    )

    data class TrackInfo(
        @JsonProperty("size") val size: Double,
        @JsonProperty("track") val track: String,
        @JsonProperty("bitrate") val bitrate: Int,
        @JsonProperty("duration") val duration: String,
        @JsonProperty("artistName") val artistName: String,
        @JsonProperty("playbackEnabled") val playbackEnabled: Boolean,
        @JsonProperty("downloadEnabled") val downloadEnabled: Boolean,
        @JsonProperty("imageJpg") val imageJpg: String?,
        @JsonProperty("imageWebp") val imageWebp: String?,
        @JsonProperty("explicit") val explicit: Boolean,
        @JsonProperty("artistId") val artistId: Long,
        @JsonProperty("isArtistForeignAgent") val isArtistForeignAgent: Boolean
    )
}
