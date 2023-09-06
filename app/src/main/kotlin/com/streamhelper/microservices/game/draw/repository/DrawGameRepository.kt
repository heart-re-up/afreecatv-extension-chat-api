package com.streamhelper.microservices.game.draw.repository

import com.streamhelper.microservices.game.draw.model.entity.DrawGame
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional(readOnly = true)
interface DrawGameRepository : JpaRepository<DrawGame, UUID> {

    fun existsDrawGameById(drawGameId: UUID): Boolean

    fun findDrawGamesByAccountId(accountId: UUID): Collection<DrawGame>
    fun findDrawGameById(drawGameId: UUID): DrawGame?

    fun deleteDrawGameById(drawGameId: UUID): Boolean
}