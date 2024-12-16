package com.example.authViaOTP.Service

import com.example.authViaOTP.DTO.EmailDetails
import com.example.authViaOTP.DTO.OtpVerfication
import com.example.authViaOTP.DTO.UserMail
import com.example.authViaOTP.Entity.OtpEntity
import com.example.authViaOTP.Repository.OtpRepository
import com.example.authViaOTP.Security.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class AuthService(private val otpRepository: OtpRepository) {

    @Autowired
    private val jwtUtil: JwtUtil? = null
    @Autowired
    private lateinit var emailService: EmailService



    fun requestOtp(request: UserMail): String {

        val email: String = request.email

        if (verifyEmail(email)) {

            val existingOtp = otpRepository.findByEmail(email)

            if (existingOtp != null) {
                if (existingOtp.expiry.isBefore(LocalDateTime.now())) {
                    otpRepository.delete(existingOtp)
                } else {
                    return "OTP already sent to email. Please check your inbox."
                }
            }

            val otp = generateOtp()
            val hashedOtp = hashOtp(otp)

            otpRepository.save(
                OtpEntity(
                    email = email, otp = hashedOtp,
                    expiry = LocalDateTime.now().plusMinutes(5)
                )
            )
            val emailDetails = EmailDetails(
                recipient = email,
                msgBody = "Your OTP id $otp",
                subject = "OTP for logging in"
            )
            emailService.sendSimpleMail(emailDetails)
            return "OTP sent to email $otp"
        }
        return "Enter valid mail id"
    }

    fun verifyOtp(verification: OtpVerfication): ResponseEntity<Map<String, String>> {

        val otpDetails: OtpEntity? = otpRepository.findByEmail(verification.email)

        if (otpDetails == null) {
            throw Exception("User Not found")
        } else if (otpDetails.expiry < LocalDateTime.now()) {
            throw Exception("OTP expired")
        } else if (!verifyHashedOtp(otpDetails.otp, verification.otp)) {
            throw Exception("Invalid OTP")
        }

        otpRepository.delete(otpDetails)

        val token = JwtUtil.generateToken(verification.email)

        val response = mapOf("token" to token)
        return ResponseEntity.ok(response)
    }

    private fun verifyEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }

    private fun generateOtp(): String {
        return (100000..999999).random().toString()
    }

    private fun hashOtp(otp: String): String {
        return otp.hashCode().toString()
    }

    private fun verifyHashedOtp(storedHash: String, otp: String): Boolean {
        return storedHash == hashOtp(otp)
    }
}



