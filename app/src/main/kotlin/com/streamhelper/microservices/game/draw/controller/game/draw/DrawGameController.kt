package com.streamhelper.microservices.game.draw.controller.game.draw

import com.streamhelper.microservices.game.draw.model.dto.DTODrawGameCreate
import com.streamhelper.microservices.game.draw.model.ro.RoDrawGame
import com.streamhelper.microservices.game.draw.service.DrawGameService
import com.streamhelper.microservices.game.draw.service.DrawGameSettingService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/api/v1/games/draw")
class DrawGameController(
    private val drawGameService: DrawGameService,
    private val drawGameSettingService: DrawGameSettingService,
) {

    @Secured("ROLE_USER")
    @PostMapping("/settings")
    fun newDrawGameSetting() {
        drawGameSettingService.createSetting()
    }

    @Secured("ROLE_USER")
    @PostMapping("/settings")
    fun getDrawGameSetting(@PathVariable @Valid @NotEmpty id: UUID) =
        drawGameSettingService.getSetting(id)
            .let { RenderedSEtting }

    @Secured("ROLE_USER")
    @GetMapping
    fun getAllGames() =
        drawGameService.getDrawGamesByAccount()
            .map { RoDrawGame(it) }
            .let { ResponseEntity.ok(it) }

    @Secured("ROLE_USER")
    @PostMapping
    fun newDrawGame(@RequestBody body: DTODrawGameCreate) =
        drawGameService.createDrawGame(body.settingId)
            .let { ResponseEntity.ok(Unit) }
}