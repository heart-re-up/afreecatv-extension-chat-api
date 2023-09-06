package com.streamhelper.microservices.game.draw.model.ro

import com.streamhelper.microservices.game.draw.model.entity.DrawPaper

data class RoDrawPaper(
    val index: Int,
    val title: String,
    val valuable: Boolean,
) {
    constructor(drawPaper: DrawPaper) : this(
        drawPaper.index,
        drawPaper.title,
        drawPaper.valuable,
    )
}
