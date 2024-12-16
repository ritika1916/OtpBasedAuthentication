package com.example.authViaOTP.DTO

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails (private  val recipient:String,
        private  val msgBody:String,
        private  val subject:String,){

    fun getRecipient(): String? {
            return recipient
    }
    fun getMsgBody(): String? {
        return msgBody
    }
    fun getSubject(): String? {
        return subject;
    }


}