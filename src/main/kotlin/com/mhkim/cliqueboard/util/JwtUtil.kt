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
//    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
//    private val expiration = 1000 * 60 * 60 //1 hour

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret))

    fun generateToken(username: String, role: String): String {
        val claims = mapOf("role" to role)
        val now = Date()
        val expiry = Date(now.time + expiration)

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiry)
//            .signWith(secretKey)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractUsername(token: String): String =
        extractAllClaims(token).subject

    fun extractRole(token: String): String =
        extractAllClaims(token)["role"] as String

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