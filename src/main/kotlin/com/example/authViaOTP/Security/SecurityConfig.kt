package com.example.authViaOTP.Security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtUtil: JwtUtil) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests { auth ->
                auth
                    // Allow unauthenticated access to OTP endpoints
                    .requestMatchers("/auth/request-otp", "/auth/verify-otp","/error").permitAll()
                    // Restrict access to admin endpoints to users with "user" role
                    .requestMatchers("/user/**").hasRole("user")
                    // Require authentication for all other endpoints
                    .anyRequest().authenticated()
            }
            .sessionManagement { session ->
                // Set session management to stateless
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .httpBasic { it.disable() } // Disable HTTP Basic authentication
            .formLogin { it.disable() } // Disable form-based login
            .addFilterBefore(
                JwtAuthenticationFilter(jwtUtil),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }

private fun corsConfiguration(): CorsConfiguration {
    val corsConfig = CorsConfiguration()
    corsConfig.apply {
        addAllowedOrigin("http://localhost:8080")  // Allow localhost:8080
        addAllowedMethod("GET")  // Allow GET method
        addAllowedMethod("POST")  // Allow POST method
        addAllowedMethod("PUT")  // Allow PUT method (if needed)
        addAllowedMethod("DELETE")  // Allow DELETE method (if needed)
        addAllowedHeader("*")  // Allow all headers
        allowCredentials = true  // Allow credentials (cookies, authorization headers, etc.)
    }
    return corsConfig
}


}