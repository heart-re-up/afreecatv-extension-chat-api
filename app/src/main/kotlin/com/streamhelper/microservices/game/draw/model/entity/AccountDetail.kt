package com.streamhelper.microservices.game.draw.model.entity

import com.streamhelper.microservices.game.draw.model.entity.mapped.PrimaryKeyEntity
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne

/**
 * 메시지 서버를 사용하는 유저. 즉 다른 서버입니다.
 */
@Entity
class AccountDetail(
    account: Account,
    phoneMobile: String,
    wallets: Collection<Wallet> = listOf(),
) : PrimaryKeyEntity() {

    /**
     * 사용자 계정
     */
    @OneToOne
    @JoinColumn(name = "accountId")
    val account: Account = account


}