package com.streamhelper.microservices.game.draw.model.entity

import com.streamhelper.microservices.game.draw.model.entity.mapped.PrimaryKeyEntity
import jakarta.persistence.*

@Entity
class DrawPrize(
    name: String,
    count: Int,
    valuable: Boolean,
    game: DrawGame,
    sound: GameSound
) : PrimaryKeyEntity() {

    @Column
    var name: String = name

    @Column
    var count: Int = count

    @Column
    var valuable: Boolean = valuable

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "drawGameId")
    var game: DrawGame = game

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameSoundId")
    var sound: GameSound = sound
}