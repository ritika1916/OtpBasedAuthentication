package com.example.authViaOTP.Controller

import com.example.authViaOTP.DTO.UserMail
import com.example.authViaOTP.DTO.OtpVerfication
import com.example.authViaOTP.Service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(private val authService:AuthService) {

    @PostMapping("/request-otp")
    fun requestOtp(@RequestBody request: UserMail): ResponseEntity<String> {
        val message = authService.requestOtp(request)
        return ResponseEntity.ok(message)

    }
    @PostMapping("/verify-otp")
    fun verifyOtp(@RequestBody verification: OtpVerfication): ResponseEntity<ResponseEntity<Map<String, String>>> {
        val token = authService.verifyOtp(verification)
        return ResponseEntity.ok(token)
    }

}