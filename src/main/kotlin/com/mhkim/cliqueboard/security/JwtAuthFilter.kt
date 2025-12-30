package com.mhkim.cliqueboard.security
import com.mhkim.cliqueboard.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtUtil: JwtUtil
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authHeader = request.getHeader("Authorization")
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                val token = authHeader.substring(7)

                if (jwtUtil.isTokenValid(token) && SecurityContextHolder.getContext().authentication == null) {
                    val username = jwtUtil.extractUsername(token)
                    val role = jwtUtil.extractRole(token)

                    val auth = UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        listOf(SimpleGrantedAuthority("ROLE_$role"))
                    )
                    SecurityContextHolder.getContext().authentication = auth

                }
            }
            filterChain.doFilter(request, response)
        } catch (ex: io.jsonwebtoken.JwtException) {
            logger.warn("JWT authentication failed: ${ex.message}")
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token")
            return
        }

    }

}