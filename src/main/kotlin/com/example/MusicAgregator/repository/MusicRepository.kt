package com.example.MusicAgregator.repository

import com.example.MusicAgregator.model.MusicModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MusicRepository: JpaRepository<MusicModel,Long> {

}