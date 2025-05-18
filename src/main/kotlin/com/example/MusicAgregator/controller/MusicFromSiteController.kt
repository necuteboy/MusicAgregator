package com.example.MusicAgregator.controller

import com.example.MusicAgregator.model.*
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/site")
class MusicFromSiteController {

    val restTemplate: RestTemplate = RestTemplate()

    @PostMapping("/get") // Endpoint mapping
    fun getMusicFromSite(@RequestBody request: RequestBodyForMusicInfo): ResponseEntity<String> {
        println("ЗАШЛИ В МЕТОД ПОЛУЧЕНИЯ ТРЕКОВ")
        val music: ResponseEntity<ResponseForMusicInfo> = restTemplate.postForEntity(
            "https://zaycev.net/api/external/track/filezmeta",
            request,
            ResponseForMusicInfo::class.java
        )
        val result: ResponseEntity<ResponseMusic> = restTemplate.getForEntity(
            "https://zaycev.net/api/external/track/play/${music.body!!.tracks[0].streaming}",
            ResponseMusic::class.java
        )

        return if (result.statusCode.is2xxSuccessful) {
            val responseMusic = result.body
            ResponseEntity.ok(responseMusic?.url)
        } else {
            // Handle error response
            ResponseEntity.status(result.statusCode).body("Error: ${result.statusCode}")
        }
    }
    @GetMapping("/get/all") // Endpoint mapping
    fun getAllMusicFromMusicSet(@RequestParam number: String,@RequestParam name: String): ResponseEntity<List<String>> {
        val result: ResponseEntity<ResponseMusicSetsModel> = restTemplate.getForEntity(
            "https://zaycev.net/api/external/pages/musicset/item?limitMusicsets=${number}&url=${name}",
            ResponseMusicSetsModel::class.java
        )

        return if (result.statusCode.is2xxSuccessful) {
            val responseMusic = result.body
            ResponseEntity.ok(responseMusic?.tracks?.trackIds?.map { it.toString() })
        } else {
            // Handle error response
            ResponseEntity.status(result.statusCode).body(listOf("Error: ${result.statusCode}"))
        }
    }
    @PostMapping("/get/by-track-ids") // Endpoint mapping
    fun getAllMusicByTrackIds(@RequestBody request: RequestBodyForMusicInfo): ResponseEntity<List<String>> {
        val result: ResponseEntity<ResponseForMusicInfo> = restTemplate.postForEntity(
            "https://zaycev.net/api/external/track/filezmeta",
            request,
            ResponseForMusicInfo::class.java
        )

        return if (result.statusCode.is2xxSuccessful) {
            val responseMusic = result.body
            ResponseEntity.ok(responseMusic?.tracks?.map { it.streaming })
        } else {
            // Handle error response
            ResponseEntity.status(result.statusCode).body(listOf("Error: ${result.statusCode}"))
        }
    }
    @GetMapping("/get/top")
    fun getTopTracks(
        @RequestParam page: String,
        @RequestParam limit: String,
        @RequestParam period: String,
        @RequestParam entity: String
    ): ResponseEntity<ResponseForControllerTopTrack> {
        val result: ResponseEntity<ResponseTopTracks> = restTemplate.getForEntity(
            "https://zaycev.net/api/external/pages/index/top?page=${page}&limit=${limit}&period=${period}&entity=${entity}",
            ResponseTopTracks::class.java
        )
        println("ЗАШЛИ В МЕТОД ТОП ТРЕКОВ")

        if (!result.statusCode.is2xxSuccessful || result.body == null) {
            return ResponseEntity.status(result.statusCode).build()
        }

        val responseMusic = result.body!!
        val tracks = responseMusic.trackIds.mapNotNull { trackId ->
            responseMusic.tracksInfo[trackId.toString()]?.let { trackInfo ->
                ResponseForControllerTopTrack.Tracks(
                    trackId = trackId.toString(),
                    artist = trackInfo.artistName,
                    trackName = trackInfo.track
                )
            }
        }

        val response = ResponseForControllerTopTrack(
            tracks = tracks
        )

        return ResponseEntity.ok(response)
    }
}