package com.streamhelper.microservices.game.draw.service

import com.streamhelper.microservices.game.draw.exception.MyAccountNotFoundException
import com.streamhelper.microservices.game.draw.expansions.spring.principal
import com.streamhelper.microservices.game.draw.model.entity.Account
import com.streamhelper.microservices.game.draw.model.entity.DrawGameSetting
import com.streamhelper.microservices.game.draw.model.so.SoDrawGameSettingCreate
import com.streamhelper.microservices.game.draw.repository.AccountRepository
import com.streamhelper.microservices.game.draw.repository.DrawGameSettingRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DrawGameSettingService(
    private val accountRepository: AccountRepository,
    private val settingRepository: DrawGameSettingRepository
) {

    fun getSetting(drawGameSettingId: UUID): DrawGameSetting? {
        return settingRepository.findDrawGameSettingById(drawGameSettingId)
    }

    fun createSetting(so: SoDrawGameSettingCreate? = null) {
        val account = accountRepository.findAccountById(principal.accountId)
            ?: throw MyAccountNotFoundException(principal.accountId)

        // 세팅 생성
        val resolvedSo = so ?: settingRepository.countDrawGameSettingsByAccount(account)
            .let { SoDrawGameSettingCreate.default("새 게임 세팅 (${it + 1})") }
        val setting = resolvedSo.toEntity(account)
        settingRepository.save(setting)

        // 상품 설정
        resolvedSo.getPrizeSettings(setting).also { setting.prizes.addAll(it) }
    }

    fun SoDrawGameSettingCreate.toEntity(account: Account): DrawGameSetting {
        return DrawGameSetting(
            this.title,
            this.total,
            account
        )
    }
}