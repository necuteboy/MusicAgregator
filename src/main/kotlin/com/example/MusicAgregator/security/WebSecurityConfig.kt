package com.example.MusicAgregator.security

import com.example.MusicAgregator.config.PasswordEncoderConfiguration
import com.example.MusicAgregator.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Service
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Service
@Configuration
class WebSecurityConfig (
    private val userService: UserService,
    private val jwtRequestFilter: JwtRequestFilter,
    private val passwordEncoder: PasswordEncoderConfiguration
) {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .csrf { it.disable() }
            .cors {
                val source = CorsConfigurationSource { request: HttpServletRequest ->
                    val config = CorsConfiguration().apply {
                        allowedOrigins = listOf("http://localhost:5173")
                        allowedHeaders = listOf("*")
                        allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    }
                    config
                }
                it.configurationSource(source)
            }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/auth/register").permitAll()
                    .requestMatchers("/auth/login").permitAll()
                    .requestMatchers("/music/**").authenticated()
                    .anyRequest().permitAll()
            }
            .formLogin { it.disable() }
            .logout { logout ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
            }
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
        return httpSecurity.build()
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder.passwordEncoder())
        println(passwordEncoder.passwordEncoder())
        daoAuthenticationProvider.setUserDetailsService(userService)
        return daoAuthenticationProvider
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}