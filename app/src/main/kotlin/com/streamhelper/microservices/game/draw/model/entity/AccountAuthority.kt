package com.streamhelper.microservices.game.draw.model.entity

import com.streamhelper.microservices.game.draw.model.entity.mapped.PrimaryKeyEntity
import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority

/**
 * 메시지 서버를 사용하는 유저. 즉 다른 서버입니다.
 */
@Entity
class AccountAuthority(
    authority: String,
    account: Account,
) : PrimaryKeyEntity() {

    companion object {
        const val PREFIX = "ROLE_"
    }

    constructor(authority: SimpleGrantedAuthority, account: Account) : this(authority.authority, account)

    @Column(nullable = false)
    var authority: String = PREFIX + authority

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accountId", nullable = false)
    var account: Account = account

}