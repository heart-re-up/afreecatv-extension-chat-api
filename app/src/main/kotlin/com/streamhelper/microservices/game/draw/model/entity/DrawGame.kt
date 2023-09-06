package com.streamhelper.microservices.game.draw.model.entity

import com.streamhelper.microservices.game.draw.model.entity.mapped.PrimaryKeyEntity
import jakarta.persistence.*

@Entity
class DrawGame(
    title: String,
    papers: Collection<DrawPaper>,
    account: Account,
) : PrimaryKeyEntity() {

    @Column
    var title: String = title

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "game")
    var papers: MutableCollection<DrawPaper> = papers.toMutableList()

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "game")
    var history: MutableCollection<DrawHistory> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    val account: Account = account

    val total: Int get() = papers.size

    val countOpened: Int get() = papers.filter { it.opened }.size
}