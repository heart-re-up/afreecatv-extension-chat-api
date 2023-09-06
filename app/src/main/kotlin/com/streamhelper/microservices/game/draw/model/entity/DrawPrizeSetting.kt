package com.streamhelper.microservices.game.draw.model.entity

import com.streamhelper.microservices.game.draw.model.entity.mapped.PrimaryKeyEntity
import jakarta.persistence.*

@Entity
class DrawPrizeSetting(
    drawGameSetting: DrawGameSetting,
    name: String,
    count: Int,
    valuable: Boolean,
    sound: GameSound? = null,
    price: Long? = null,
) : PrimaryKeyEntity() {

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "drawGameSettingId")
    var setting: DrawGameSetting = drawGameSetting

    @Column
    var name: String = name

    @Column
    var count: Int = count

    /**
     * 이 상품이 가치있는 상품인지 여부 입니다.
     * 보통 꽝을 제외하고는 모두 가치있는 상품으로 취급합니다.
     */
    @Column
    var valuable: Boolean = valuable

    /**
     * 잇 상품의 가격입니다. 설정하지 않아도 됩니다.
     * 설정하게 되면 게임 통계에서 사용됩니다.
     */
    @Column
    var price: Long? = price

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "gameSoundId")
    var sound: GameSound? = sound
}