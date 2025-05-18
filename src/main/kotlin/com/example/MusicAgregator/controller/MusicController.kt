package com.example.MusicAgregator.controller

import com.example.MusicAgregator.dto.MusicDto
import com.example.MusicAgregator.service.MusicService
import com.example.MusicAgregator.storage.StorageService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/music")
class MusicController(
    val musicService: MusicService,
    val storageService: StorageService
) {
    @GetMapping("/get-music")
    fun getMusic(@RequestParam id: Long): MusicDto = musicService.getMusicById(id)

    @PostMapping("/save-music")
    fun saveMusic(musicDto: MusicDto) = musicService.saveMusic(musicDto)

    @DeleteMapping("/delete-music")
    fun deleteMusic(id: Long) = musicService.deleteMusic(id)

    @PostMapping("/upload-music")
    fun uploadMusic(@RequestBody file: MultipartFile) {
        storageService.uploadMp3File(file)
    }

    @GetMapping("/download-music")
    fun downloadMusic(@RequestParam name: String): ResponseEntity<ByteArray> {
        val fileContent = storageService.getFile(name)
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.mp3")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(fileContent)
    }

    @GetMapping("/get-all")
    fun getAllMusic(): ResponseEntity<List<MusicDto>> {
        val response = musicService.getAllMusic()
        return ResponseEntity.ok(response)
    }

}