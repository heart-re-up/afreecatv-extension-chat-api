package com.streamhelper.microservices.game.draw.service

import com.streamhelper.microservices.game.draw.expansions.spring.principal
import com.streamhelper.microservices.game.draw.model.entity.DrawGame
import com.streamhelper.microservices.game.draw.repository.DrawGameRepository
import com.tinteccnc.util.MyLogger
import org.springframework.stereotype.Service
import java.util.*

@Service
class DrawGameService(
    val repository: DrawGameRepository
) : MyLogger {

    fun createDrawGame(settingId: UUID) {
        info("createDrawGame: $settingId")
    }

    fun getDrawGamesByAccount(): Collection<DrawGame> {
        return repository.findDrawGamesByAccountId(principal.accountId)
    }

    fun getDrawGames() {

    }
}