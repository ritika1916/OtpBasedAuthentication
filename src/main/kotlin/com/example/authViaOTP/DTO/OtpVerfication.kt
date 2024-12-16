package com.example.authViaOTP.DTO

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.springframework.stereotype.Component

@AllArgsConstructor
@NoArgsConstructor
@Component
@Getter
@Setter
class OtpVerfication {

    val email: String=""
    val otp: String=""

}