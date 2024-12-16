package com.example.authViaOTP.Security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:8080")  // Allow localhost:8080
            .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow necessary methods
            .allowedHeaders("*")  // Allow all headers
            .allowCredentials(true)  // Allow credentials
    }
}
