package com.example.authViaOTP.Security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
object JwtUtil {
    private const val SECRET_KEY = "ySb4G5TwP7lxVe7YjdU8dK+tkD7x5JFCyFJ+Xf6YrXs="

    fun generateToken(email: String): String {
        val claims = mutableMapOf<String, Any>()
        claims["email"] = email
        claims["roles"] = listOf("user")  //Fixed role as user for now

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(email)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 86400000)) // 24 hours expiration
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact()
    }


    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    // Extract user email from the token
    fun extractEmail(token: String): String {
        val claims = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .body
        return claims["email"] as String
    }

    // Extract user roles from the token
    fun extractRoles(token: String): List<String> {
        val claims = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .body
        return claims["roles"] as List<String>
    }
}

