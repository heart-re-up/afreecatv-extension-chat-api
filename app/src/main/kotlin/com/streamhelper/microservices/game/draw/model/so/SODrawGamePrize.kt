package com.streamhelper.microservices.game.draw.model.so

import com.streamhelper.microservices.game.draw.model.entity.GameSound

data class SODrawGamePrize(
    val title: String,
    val count: Int,
    val valuable: Boolean,
    val sound: GameSound? = null,
)
