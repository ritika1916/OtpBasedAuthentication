package com.example.authViaOTP.Entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "otp")
data class OtpEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long=0,
    val email: String,
    val otp: String,
    val expiry: LocalDateTime
){
    constructor() : this(email = "", otp = "", expiry = LocalDateTime.now())
}
