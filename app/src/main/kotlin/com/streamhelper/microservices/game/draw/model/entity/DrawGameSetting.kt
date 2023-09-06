package com.streamhelper.microservices.game.draw.model.entity

import com.streamhelper.microservices.game.draw.model.entity.mapped.PrimaryKeyEntity
import jakarta.persistence.*

@Entity
class DrawGameSetting(
    title: String,
    total: Int,
    account: Account,
) : PrimaryKeyEntity() {

    @Column
    var title: String = title

    @Column
    var total: Int = total

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "setting")
    var prizes: MutableCollection<DrawPrizeSetting> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    val account: Account = account

}