package com.example.MusicAgregator.service

import com.example.MusicAgregator.model.CronJob
import com.example.MusicAgregator.repository.CronJobRepository
import org.springframework.stereotype.Service


@Service
class CronJobService(val cronJobRepository: CronJobRepository) {

    fun getCronJob(cronJobId: Long): CronJob {
        return getCronJobOrThrow(cronJobId)
    }

    fun changeCron(cronJobId: Long, cron: String?): Long {
        val cronJob: CronJob = getCronJobOrThrow(cronJobId)
        cronJob.cron = cron
        return cronJobRepository.save(cronJob).id
    }

    private fun getCronJobOrThrow(cronJobId: Long): CronJob {
        return cronJobRepository.findById(cronJobId).get()
    }
}