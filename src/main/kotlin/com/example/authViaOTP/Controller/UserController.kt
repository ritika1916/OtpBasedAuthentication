package com.example.authViaOTP.Controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user")
class UserController {
    @GetMapping
    fun Hello():String{
        return "Hello world!"
    }
}