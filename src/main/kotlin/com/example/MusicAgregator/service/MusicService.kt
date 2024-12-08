package com.example.MusicAgregator.service

import com.example.MusicAgregator.dto.MusicDto
import com.example.MusicAgregator.mapper.MusicMapper
import com.example.MusicAgregator.repository.MusicRepository
import org.springframework.stereotype.Service

@Service
class MusicService(
    val musicRepository: MusicRepository,
    val musicMapper: MusicMapper
) {
    fun getMusicById(id: Long):MusicDto = musicMapper.mapToMusicDto(musicRepository.findById(id).get())

    fun saveMusic(music: MusicDto) = musicRepository.save(musicMapper.mapToMusicModel(music))

    fun deleteMusic(id: Long) = musicRepository.deleteById(id)

}