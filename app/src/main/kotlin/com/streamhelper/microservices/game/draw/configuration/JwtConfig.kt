package com.streamhelper.microservices.game.draw.configuration

import com.streamhelper.microservices.game.draw.configuration.properties.JwtProperty
import com.streamhelper.microservices.game.draw.security.JwtOptions
import com.streamhelper.microservices.game.draw.security.JwtProvider
import com.streamhelper.microservices.game.draw.service.AccountDetailsService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
data class JwtConfig(
    val property: JwtProperty
) {
    val jwtOptionsToken = JwtOptions(property.token)

    /**
     * access_token, refresh_token 등을 관리하는 JWT 제공자 입니다.
     */
    @Bean
    fun jwtProviderToken(accountDetailsService: AccountDetailsService) =
        JwtProvider(jwtOptionsToken, accountDetailsService)

}

