package com.streamhelper.microservices.game.draw.model.ro

import com.streamhelper.microservices.game.draw.model.entity.DrawGame

data class RoDrawGame(
    val title: String,
    val papers: Collection<RoDrawPaper>,

    ) {
    constructor(drawGame: DrawGame) : this(
        drawGame.title,
        drawGame.papers.map { RoDrawPaper(it) },
    )
}
