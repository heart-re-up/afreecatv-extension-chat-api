package com.streamhelper.microservices.game.draw.service

import com.streamhelper.microservices.game.draw.model.business.security.AccountDetails
import com.streamhelper.microservices.game.draw.repository.AccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*


interface AccountDetailsService : UserDetailsService {
    fun loadUserByAccountId(accountId: UUID): AccountDetails
    fun loadUserByAccountEmail(email: String): AccountDetails
}

/**
 * UUID 문자열로 사용자를 조회합니다.
 */
@Service
class AccountDetailsServiceImpl(
    private val accountRepository: AccountRepository,
) : AccountDetailsService {

    override fun loadUserByAccountId(accountId: UUID): AccountDetails {
        return accountRepository.findAccountById(accountId)
            ?.let { AccountDetails(it) }
            ?: throw UsernameNotFoundException("아이디($accountId) 일치 계정이 없습니다.")
    }

    override fun loadUserByAccountEmail(email: String): AccountDetails {
        return accountRepository.findAccountByEmailValue(email)
            ?.let { AccountDetails(it) }
            ?: throw UsernameNotFoundException("이메일($email) 일치 계정이 없습니다.")
    }

    override fun loadUserByUsername(email: String?): UserDetails {
        return if (email == null) throw UsernameNotFoundException("해당 이름과 일치하는 계정이 없습니다.")
        else loadUserByAccountEmail(email)
    }

}