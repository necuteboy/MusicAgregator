package com.example.MusicAgregator.dto

data class RegistrationUserDto(
    val username: String,
    val password: String,
    val confirmPassword: String,
    val email: String,
    val gender: String
)