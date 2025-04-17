package com.example.MusicAgregator.controller

import com.example.MusicAgregator.model.ResponseMusic
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/site")
class MusicFromSiteController {

    val restTemplate: RestTemplate = RestTemplate()

    @GetMapping("/get-from-zyac") // Endpoint mapping
    fun getMusicFromSite(): ResponseEntity<String> {
        println("AAAAAAA")
        val result: ResponseEntity<ResponseMusic> = restTemplate.getForEntity("https://zaycev.net/api/external/track/play/de37d3698975470a", ResponseMusic::class.java)

        // Example handling of the result
        return if (result.statusCode.is2xxSuccessful) {
            // Handle successful result
            val responseMusic = result.body
            ResponseEntity.ok("Success: ${responseMusic?.url}") // replace someProperty with the actual field name
        } else {
            // Handle error response
            ResponseEntity.status(result.statusCode).body("Error: ${result.statusCode}")
        }
    }
}