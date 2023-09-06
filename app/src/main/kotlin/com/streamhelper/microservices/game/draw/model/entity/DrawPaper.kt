package com.streamhelper.microservices.game.draw.model.entity

import com.streamhelper.microservices.game.draw.model.entity.mapped.PrimaryKeyEntity
import jakarta.persistence.*

@Entity
class DrawPaper(
    index: Int,
    title: String,
    valuable: Boolean,
    game: DrawGame,
) : PrimaryKeyEntity() {

    @Column
    var index: Int = index

    @Column
    var title: String = title

    @Column
    var valuable: Boolean = valuable

    @Column
    var opened: Boolean = false

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "drawGameId")
    var game: DrawGame = game
}