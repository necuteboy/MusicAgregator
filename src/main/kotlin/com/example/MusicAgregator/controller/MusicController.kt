package com.example.MusicAgregator.controller

import com.example.MusicAgregator.dto.MusicDto
import com.example.MusicAgregator.service.MusicService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController(value = "/music")
class MusicController(
    val musicService: MusicService
) {
    @GetMapping(value = ["/get-music"])
    fun getMusic(id: Long): MusicDto = musicService.getMusicById(id)

    @PostMapping(value = ["save-music"])
    fun saveMusic(musicDto: MusicDto) = musicService.saveMusic(musicDto)

    @DeleteMapping(value = ["delete-music"])
    fun saveMusic(id: Long) = musicService.deleteMusic(id)

}