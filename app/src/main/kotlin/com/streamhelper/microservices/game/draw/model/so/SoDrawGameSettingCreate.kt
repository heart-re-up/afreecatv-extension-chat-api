package com.streamhelper.microservices.game.draw.model.so

import com.streamhelper.microservices.game.draw.model.entity.DrawGameSetting
import com.streamhelper.microservices.game.draw.model.entity.DrawPrizeSetting

data class SoDrawGameSettingCreate(
    val title: String,
    val total: Int,
    val prizes: Collection<SODrawGamePrize>,
) {
    companion object {
        fun default(title: String): SoDrawGameSettingCreate {
            val prizes = listOf(
                SODrawGamePrize("당첨", 3, true),
                SODrawGamePrize("꽝", 97, false)
            )
            return SoDrawGameSettingCreate(
                title,
                100,
                prizes
            )
        }
    }

    fun getPrizeSettings(drawGameSetting: DrawGameSetting): Collection<DrawPrizeSetting> {
        return prizes.map {
            DrawPrizeSetting(
                drawGameSetting,
                it.title,
                it.count,
                it.valuable,
                it.sound,
            )
        }
    }
}
