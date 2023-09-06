package com.streamhelper.microservices.game.draw.app

import com.streamhelper.microservices.game.draw.exception.MyAccountNotFoundException
import com.streamhelper.microservices.game.draw.model.business.security.UserAuthority
import com.streamhelper.microservices.game.draw.repository.AccountRepository
import com.streamhelper.microservices.game.draw.security.JwtProvider
import com.streamhelper.microservices.game.draw.service.AccountService
import com.tinteccnc.util.MyLogger
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * 서버가 시작될 때 기초 데이터를 삽입합니다.
 */
@Component
class DataInitializer(
    private val accountRepo: AccountRepository,
    private val accountService: AccountService,
    private val jwtProvider: JwtProvider,
) : ApplicationRunner, MyLogger {

    override fun run(args: ApplicationArguments?) {
        initAccount()
    }

    @Transactional
    fun initAccount() {
        try {
            accountService.findAccountByEmail("devking@tinteccnc.com")
        } catch (e: MyAccountNotFoundException) {
            accountService.createAccount(
                "슈퍼유저1",
                "12345678",
                "su@gmail.com",
                listOf(UserAuthority.USER, UserAuthority.ADMIN, UserAuthority.SUPERUSER).map { it.name },
                true
            )
            accountService.createAccount(
                "관리자1",
                "12345678",
                "admin@gmail.com",
                listOf(UserAuthority.USER, UserAuthority.ADMIN).map { it.name },
                true
            )
            accountService.createAccount(
                "방송인1",
                "12345678",
                "broadcaster@gmail.com",
                listOf(UserAuthority.USER).map { it.name },
                true
            )
            accountService.createAccount(
                "사용자1",
                "12345678",
                "user@gmail.com",
                listOf(UserAuthority.USER).map { it.name },
                true
            )
        } catch (e: Exception) {
            error(e.message ?: e::class.simpleName ?: "unknown error")
        }
    }

}