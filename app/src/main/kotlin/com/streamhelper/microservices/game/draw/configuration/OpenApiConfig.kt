package com.streamhelper.microservices.game.draw.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun openAPI() = Info().apply {
        title = "BitCon API Document"
        version = "v0.0.1"
        description = "빛컨 IoT API 명세입니다."
    }.let {
        OpenAPI().apply {
            components = Components()
            info(it)
        }
    }
}