package com.streamhelper.microservices.game.draw.service

import com.streamhelper.microservices.game.draw.exception.MyAccountNotFoundException
import com.streamhelper.microservices.game.draw.model.business.security.AccountRoles
import com.streamhelper.microservices.game.draw.model.entity.Account
import com.streamhelper.microservices.game.draw.model.entity.embeddable.Email
import com.streamhelper.microservices.game.draw.model.ro.RoAccount
import com.streamhelper.microservices.game.draw.repository.AccountRepository
import com.tinteccnc.util.MyLogger
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AccountService(
    private val accountRepo: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
//    @Qualifier("token")
//    private val jwtProvider: JwtProvider,
) : MyLogger {

    @Transactional(readOnly = true)
    @Throws(MyAccountNotFoundException::class)
    fun findAccounts() = accountRepo.findAll()
        .map { RoAccount(it) }

    @Transactional(readOnly = true)
    @Throws(MyAccountNotFoundException::class)
    fun findAccountById(id: UUID) = accountRepo.findAccountById(id)
        ?: throw MyAccountNotFoundException(id)

    @Transactional(readOnly = true)
    @Throws(MyAccountNotFoundException::class)
    fun findAccountByName(name: String) = accountRepo.findAccountByName(name)
        ?: throw MyAccountNotFoundException(name)

    @Transactional(readOnly = true)
    @Throws(MyAccountNotFoundException::class)
    fun findAccountByEmail(email: String) = accountRepo.findAccountByEmail(Email(email))?.let { RoAccount(it) }
        ?: throw MyAccountNotFoundException(email)

    @Transactional(readOnly = true)
    @Throws(MyAccountNotFoundException::class)
    fun findAccountForPrincipal(email: String): Account {
        val account = accountRepo.findAccountByEmail(Email(email))
            ?: throw MyAccountNotFoundException(email)
        info("found account: $account")
        return account
    }

    @Transactional
    fun createAccount(
        name: String,
        password: String,
        email: String,
        authorities: Collection<String>,
        superuser: Boolean = false
    ): Account =
        // 1. 엔터티 작성
        Account(email, passwordEncoder.encode(password), name, authorities)
            // 2. 권한
            .also { if (superuser) it.addAuthority(AccountRoles.SUPER_USER.role) }
            // 3. 영속 처리
            .also { accountRepo.save(it) }

}