package com.streamhelper.microservices.game.draw.model.ro

import com.streamhelper.microservices.game.draw.model.entity.GameSound

class RoGameSound(
    val title: String,
    val url: String,
) {
    constructor(gameSound: GameSound) : this(
        gameSound.name,
        gameSound.url,
    )
    
}