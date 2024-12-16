package com.example.authViaOTP.Repository

import com.example.authViaOTP.Entity.OtpEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface OtpRepository: JpaRepository<OtpEntity, Long>  {

    fun findByEmail(email: String): OtpEntity?
    override fun delete(existingOtp: OtpEntity)
}