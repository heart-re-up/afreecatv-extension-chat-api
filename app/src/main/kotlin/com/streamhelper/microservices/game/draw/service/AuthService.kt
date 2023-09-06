package com.streamhelper.microservices.game.draw.service

import com.streamhelper.microservices.game.draw.exception.MyAccountNotFoundException
import com.streamhelper.microservices.game.draw.exception.MyUnauthorizedException
import com.streamhelper.microservices.game.draw.expansions.spring.hasAuthority
import com.streamhelper.microservices.game.draw.model.ro.RoLogon
import com.streamhelper.microservices.game.draw.repository.AccountRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService,
) {
    @Throws(
        MyAccountNotFoundException::class,
        MyUnauthorizedException::class
    )
    fun login(email: String, password: String): RoLogon {
        val account = accountRepository.findAccountByEmailValue(email)
            ?: throw MyAccountNotFoundException(email)
        if (passwordEncoder.matches(password, account.password)) {
            val renderedToken = tokenService.issueToken(account)
            val isAdministrator = account.grantedAuthorities.hasAuthority("ROLE_ADMIN")
            return RoLogon(renderedToken, isAdministrator)
        }
        throw MyUnauthorizedException()
    }
}