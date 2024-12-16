package com.example.authViaOTP.Security


import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {


    private fun getAuthorities(roles: List<String>): List<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority("ROLE_$it") }  // Convert roles to Spring's granted authorities
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: jakarta.servlet.FilterChain
    ) {
        if (request.requestURI.startsWith("/auth/request-otp") || request.requestURI.startsWith("/auth/verify-otp")) {
            filterChain.doFilter(request, response)
            return
        }
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)  // Extract the token part
            if (jwtUtil.validateToken(token)) {

                val email = jwtUtil.extractEmail(token)
                val roles = jwtUtil.extractRoles(token)
                val authentication = UsernamePasswordAuthenticationToken(email, null, getAuthorities(roles))
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }
}
