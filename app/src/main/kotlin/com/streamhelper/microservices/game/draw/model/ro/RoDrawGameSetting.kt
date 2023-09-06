package com.streamhelper.microservices.game.draw.model.ro

import com.streamhelper.microservices.game.draw.model.entity.DrawGameSetting

data class RoDrawGameSetting(
    val title: String,
    val prizes: Collection<RoDrawPrizeSetting>
) {
    constructor(setting: DrawGameSetting) : this(
        setting.title,
        setting.prizes.map { RoDrawPrizeSetting(it) }
    )
}
