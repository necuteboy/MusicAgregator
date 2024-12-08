package com.example.MusicAgregator.utils

import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm
import kotlin.collections.HashMap


@Component
class JwtTokenUtils {
    @Value("\${jwt.secret}")
    private val secret: String? = null

    @Value("\${jwt.expirationMs}")
    private val jwtLifetime: Duration? = null
    fun generateToken(userDetails: UserDetails): String {
        val claims: MutableMap<String, Any> = HashMap()
        val roleList = userDetails.authorities
            .stream().map { obj: GrantedAuthority -> obj.authority }
            .toList()
        claims["roles"] = roleList
        val issuedDate = Date()
        val expiredDate = Date(issuedDate.getTime() + jwtLifetime!!.toMillis())
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.username)
            .setIssuedAt(issuedDate)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun getUsername(token: String): String {
        return getAllClaimsFromToken(token).getSubject()
    }

    fun getRoles(token: String): MutableList<*>? {
        return getAllClaimsFromToken(token).get("roles", MutableList::class.java)
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
    }
}