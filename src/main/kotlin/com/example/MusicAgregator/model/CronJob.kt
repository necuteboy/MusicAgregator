package com.example.MusicAgregator.model

import jakarta.persistence.*
import lombok.Setter
import java.time.LocalDateTime


@Entity
@Table(name = "cron_job")
data class CronJob(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "cron")
    var cron: String? = null,

    @Column(name = "triggered_at")
    var triggeredAt: LocalDateTime? = null
)