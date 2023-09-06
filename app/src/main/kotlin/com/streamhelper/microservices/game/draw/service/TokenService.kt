package com.streamhelper.microservices.game.draw.service

import com.streamhelper.microservices.game.draw.model.entity.Account
import com.streamhelper.microservices.game.draw.security.JwtProvider
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val jwtProvider: JwtProvider
) {
    fun issueToken(account: Account) = jwtProvider.generateTokens(account)

    fun refreshToken(refreshToken: String) {

    }
}