package com.example.MusicAgregator.scheduler

import com.example.MusicAgregator.repository.CronJobRepository
import com.example.MusicAgregator.repository.MusicRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
class CleanUpTaskScheduler(val cronJobRepository: CronJobRepository, val musicRepository: MusicRepository) {

    @Scheduled(cron = "*/8 * * * * *")
    @Transactional
    fun cleanUpPosts() {
        val cronJob = cronJobRepository.findById(1L).orElseThrow { EntityNotFoundException() }!!
        val startJobAfter: LocalDateTime? = cronJob.triggeredAt
        if (LocalDateTime.now().isAfter(startJobAfter)) {
            musicRepository.deleteAll()
            println("Success")
            cronJob.triggeredAt = LocalDateTime.now()
            cronJobRepository.save(cronJob)
        }
    }
}