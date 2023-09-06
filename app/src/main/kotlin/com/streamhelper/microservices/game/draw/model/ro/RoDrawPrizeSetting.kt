package com.streamhelper.microservices.game.draw.model.ro

import com.streamhelper.microservices.game.draw.model.entity.DrawPrizeSetting

class RoDrawPrizeSetting(
    val name: String,
    val count: Int,
    val valuable: Boolean,
    val price: Long?,
    val sound: RoGameSound?,
) {

    constructor(drawPrizeSetting: DrawPrizeSetting) : this(
        drawPrizeSetting.name,
        drawPrizeSetting.count,
        drawPrizeSetting.valuable,
        drawPrizeSetting.price,
        drawPrizeSetting.sound?.let { RoGameSound(it) },
    )

}