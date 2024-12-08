package com.example.MusicAgregator.security

import com.example.MusicAgregator.utils.JwtTokenUtils
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.security.SignatureException
import java.util.stream.Collectors


@Component
@Slf4j
class JwtRequestFilter( val jwtTokenUtils: JwtTokenUtils) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
     override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String = request.getHeader("Authorization")
        var username: String? = null
        var jwt: String? = null
        if (authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7)
            try {
                username = jwtTokenUtils.getUsername(jwt)
            } catch (e: ExpiredJwtException) {
                logger.debug("Время жизни токена вышло")
            } catch (e: SignatureException) {
                logger.debug("Подпись неправильная")
            }
        }
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val token = UsernamePasswordAuthenticationToken(
                username,
                null,
                jwt?.let {
                    jwtTokenUtils.getRoles(it)?.stream()
                        ?.filter { it is String }
                        ?.map { role: Any? -> SimpleGrantedAuthority(role as String?) }
                        ?.collect(Collectors.toList())
                }
            )
            SecurityContextHolder.getContext().authentication = token
        }
        filterChain.doFilter(request, response)
    }
}