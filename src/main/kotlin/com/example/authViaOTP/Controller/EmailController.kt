package com.example.authViaOTP.Controller

import com.example.authViaOTP.DTO.EmailDetails
import com.example.authViaOTP.Service.EmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
class EmailController {
    @Autowired
    private lateinit var emailService: EmailService


    @PostMapping("/sendMail")
    fun sendMail(@RequestBody details: EmailDetails?): String {
        val status = emailService.sendSimpleMail(details!!)
        return status
    }



}