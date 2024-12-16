package com.example.authViaOTP.Service

import com.example.authViaOTP.DTO.EmailDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService {
    @Autowired
    private val javaMailSender: JavaMailSender? = null

    @Value("\${spring.mail.username}")
    private val sender: String? = null

    fun sendSimpleMail(details: EmailDetails): String {
        // Try block to check for exceptions

        try {
            // Creating a simple mail message

            val mailMessage = SimpleMailMessage()

            // Setting up necessary details
            mailMessage.from = sender
            mailMessage.setTo(details.getRecipient())
            mailMessage.setText(details.getMsgBody())
            mailMessage.setSubject(details.getSubject())

            // Sending the mail
            javaMailSender!!.send(mailMessage)
            return "Mail Sent Successfully..."
        } // Catch block to handle the exceptions

        catch (e: Exception) {
            return "Error while Sending Mail"
        }
    }

}