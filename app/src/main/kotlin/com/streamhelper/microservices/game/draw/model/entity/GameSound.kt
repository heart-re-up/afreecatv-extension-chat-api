package com.streamhelper.microservices.game.draw.model.entity

import com.streamhelper.microservices.game.draw.model.entity.mapped.PrimaryKeyEntity
import jakarta.persistence.*

@Entity
class GameSound(
    name: String,
    url: String,
    account: Account? = null,
) : PrimaryKeyEntity() {

    @Column
    var name: String = name

    @Column
    var url: String = url

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = true)
    var account: Account? = account
}