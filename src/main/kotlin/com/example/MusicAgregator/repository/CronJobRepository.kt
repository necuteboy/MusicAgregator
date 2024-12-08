package com.example.MusicAgregator.repository

import com.example.MusicAgregator.model.CronJob

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CronJobRepository : JpaRepository<CronJob?, Long?>