package com.example.MusicAgregator.service

import com.example.MusicAgregator.config.PasswordEncoderConfiguration
import com.example.MusicAgregator.dto.RegistrationUserDto
import com.example.MusicAgregator.model.User
import com.example.MusicAgregator.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoderConfiguration,
) : UserDetailsService {

    fun findByUserName(name: String): User {
        return userRepository.findByName(name)
    }

    fun findById(id: Long): User {
        return userRepository.findById(id).orElseThrow { EntityNotFoundException("User not found") }
    }

    @Transactional
    fun deleteUser(id: Long) {
        val user = findById(id)
        user.isBanned = true
        userRepository.save(user)
    }

    fun checkForBan(username: String): Boolean? {
        val user = findByUserName(username)
        return user.isBanned
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByName(username)
        return org.springframework.security.core.userdetails.User(
            user.name,
            user.password,
            listOf(SimpleGrantedAuthority(user.role))
        )
    }

    @Transactional
    fun unbanUser(id: Long) {
        val user = findById(id)
        user.isBanned = false
        userRepository.save(user)
    }

    @Transactional
    fun giveModerator(id: Long) {
        val user = findById(id)
        user.role = "MODERATOR"
        userRepository.save(user)
    }

    @Transactional
    fun takeModerator(id: Long) {
        val user = findById(id)
        user.role = "USER"
        userRepository.save(user)
    }

    fun createUser(registrationUserDto: RegistrationUserDto): User {
        val user = User(
            name = registrationUserDto.username,
            password = passwordEncoder.passwordEncoder().encode(registrationUserDto.password),
            gender = registrationUserDto.gender,
            role = "USER",
            email = registrationUserDto.email
        )
        return userRepository.save(user)
    }

    fun getUserByName(username: String): Long? {
        val user = userRepository.findByName(username)
        return user.id
    }
}