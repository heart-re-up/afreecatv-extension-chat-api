package com.streamhelper.microservices.game.draw.security

import com.tinteccnc.util.MyLogger
import com.streamhelper.microservices.game.draw.model.business.security.AccountAuthentication
import com.streamhelper.microservices.game.draw.model.business.security.AccountDetails
import com.streamhelper.microservices.game.draw.service.AccountDetailsService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

/**
 * 계정과 비밀번호로 인증할 때 사용합니다.
 * 데이터베이스의 계정/비밀번호와 대조해야 하기 때문에 데이터베이스 작업이 동반됩니다.
 */
@Component
class AccountAuthenticationProvider(
    private val accountDetailsService: AccountDetailsService,
    private val passwordEncoder: PasswordEncoder,
) : AuthenticationProvider, MyLogger {

    /**
     * 계정 및 비밀번호 기반 인증입니다.
     * 비밀번호 일치 여부로 인증을 수행합니다.
     *
     * @param authentication [Authentication.getName] 이 이메일을 반환하는 객체입니다.
     */
    override fun authenticate(authentication: Authentication?): Authentication? {
        // 계정 획득
        val account = accountDetailsService.loadUserByUsername(authentication?.name)
            ?.let { it as? AccountDetails }?.account
            ?: return null
        // 비밀번호 인코드
        val encodedPassword = authentication?.credentials
            ?.takeIf { it is String }
            ?.let { passwordEncoder.encode(it as String) }
            ?: return null
        // 비밀번호를 비교해서 올바르면 인증 객체를 반환한다. 올바르지 않은 경우 null.
        return account.takeIf { it.password == encodedPassword }?.let { AccountAuthentication(it) }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return false
    }
}