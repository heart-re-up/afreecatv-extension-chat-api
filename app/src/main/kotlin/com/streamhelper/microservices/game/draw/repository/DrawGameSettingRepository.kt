package com.streamhelper.microservices.game.draw.repository

import com.streamhelper.microservices.game.draw.model.entity.Account
import com.streamhelper.microservices.game.draw.model.entity.DrawGameSetting
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional(readOnly = true)
interface DrawGameSettingRepository : JpaRepository<DrawGameSetting, UUID> {

    fun countDrawGameSettingsByAccount(account: Account): Long
    fun countDrawGameSettingsByAccountId(accountId: UUID): Long

    fun existsDrawGameSettingById(drawGameSettingId: UUID): Boolean

    fun findDrawGameSettingsByAccountId(accountId: UUID): Collection<DrawGameSetting>
    fun findDrawGameSettingById(drawGameSettingId: UUID): DrawGameSetting?

    fun deleteDrawGameSettingById(drawGameSettingId: UUID): Boolean
}