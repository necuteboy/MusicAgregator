package com.example.MusicAgregator.mapper

import com.example.MusicAgregator.dto.MusicDto
import com.example.MusicAgregator.model.MusicModel
import com.example.MusicAgregator.model.User
import org.springframework.stereotype.Component

@Component
class MusicMapper(
) {
    fun mapToMusicDto(musicModel: MusicModel): MusicDto = MusicDto(
        id = musicModel.id!!,
        name = musicModel.name,
        author = musicModel.author,
        genre = musicModel.genre,
        user = musicModel.users.id!!
    )

    fun mapToMusicModel(musicDto: MusicDto): MusicModel = MusicModel(
        id = musicDto.id,
        name = musicDto.name,
        author = musicDto.author,
        genre = musicDto.genre,
        users = User(id = musicDto.user, name = "test",role = "User", password = "test", gender = "test", email = "test")
    )
}