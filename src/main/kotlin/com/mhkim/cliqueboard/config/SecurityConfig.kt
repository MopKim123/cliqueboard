package com.mhkim.cliqueboard.config

import com.mhkim.cliqueboard.security.JwtAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.http.HttpMethod


@Configuration
class SecurityConfig(
    private val jwtAuthFilter: JwtAuthFilter
){

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain{
        http
            .csrf{ it.disable() }
            .cors {  }
            .authorizeHttpRequests {
                it.requestMatchers("/auth/**").permitAll()
                it.requestMatchers(HttpMethod.GET, "/courses").permitAll()
                it.requestMatchers("/ws/**").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
//            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)



        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}