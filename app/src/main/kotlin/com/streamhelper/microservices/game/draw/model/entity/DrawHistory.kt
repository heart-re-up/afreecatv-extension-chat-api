package com.streamhelper.microservices.game.draw.model.entity

import com.streamhelper.microservices.game.draw.model.entity.embeddable.Donation
import com.streamhelper.microservices.game.draw.model.entity.mapped.PrimaryKeyEntity
import jakarta.persistence.*

@Entity
class DrawHistory(
    game: DrawGame,
    paper: DrawPaper,
    prize: DrawPrize,
    donation: Donation,
) : PrimaryKeyEntity() {

    /**
     * 이 기록이 연관된 게임입니다.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "drawGameId")
    var game: DrawGame = game

    /**
     * 이 기록이 연관된 후원입니다.
     */
    @Embedded
    var donation: Donation = donation

    @OneToOne
    @JoinColumn(name = "drawPrizeId")
    var prize: DrawPrize = prize
}