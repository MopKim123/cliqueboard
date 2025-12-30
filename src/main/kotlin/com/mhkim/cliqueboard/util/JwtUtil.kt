package com.mhkim.cliqueboard.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey
import java.util.*

@Component
class JwtUtil(
    @Value("\${jwt.secret}") secret: String,
    @Value("\${jwt.expiration}") private val expiration: Long
) {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret))

    fun generateToken(username: String): String {
        val now = Date()
        val expiry = Date(now.time + expiration)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractUsername(token: String): String =
        extractAllClaims(token).subject

    fun isTokenValid(token: String): Boolean {
        val claims = extractAllClaims(token)
        return claims.expiration.after(Date())
    }

    fun validateToken(token: String) {
        try {
            extractAllClaims(token)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid or expired JWT")
        }
    }

    private fun extractAllClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
}
