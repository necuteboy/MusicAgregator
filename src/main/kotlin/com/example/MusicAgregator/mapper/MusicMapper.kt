package com.example.MusicAgregator.mapper

import com.example.MusicAgregator.dto.MusicDto
import com.example.MusicAgregator.model.MusicModel
import org.springframework.stereotype.Component

@Component
class MusicMapper(
) {
    fun mapToMusicDto(musicModel: MusicModel): MusicDto = MusicDto(
        id = musicModel.id,
        name = musicModel.name,
        author = musicModel.author,
        genre = musicModel.genre,
        musicFileExtract = musicModel.musicFileExtract,
        users = musicModel.users
    )

    fun mapToMusicModel(musicDto: MusicDto): MusicModel = MusicModel(
        id = musicDto.id,
        name = musicDto.name,
        author = musicDto.author,
        genre = musicDto.genre,
        musicFileExtract = musicDto.musicFileExtract,
        users = musicDto.users
    )
}