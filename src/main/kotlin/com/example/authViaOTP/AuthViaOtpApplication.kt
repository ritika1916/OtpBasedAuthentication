package com.example.authViaOTP

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.sql.DataSource


@SpringBootApplication

class AuthViaOtpApplication

fun main(args: Array<String>) {
	runApplication<AuthViaOtpApplication>(*args)
}
