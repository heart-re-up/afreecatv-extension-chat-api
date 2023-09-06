package com.streamhelper.microservices.game.draw.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class AuthenticationProviderToken(
    private val userDetailsService: UserDetailsService
) : AuthenticationProvider {

    /**
     * 토큰 기반 인증입니다.
     * - principal: token 의 audience(Account.id) 입니다.
     * - credentials: access token 입니다.
     */
    override fun authenticate(authentication: Authentication?): Authentication {
        val details = userDetailsService.loadUserByUsername(authentication?.name)
        return UsernamePasswordAuthenticationToken(
            details,
            details.password,
            details.authorities
        )
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return false
    }
}