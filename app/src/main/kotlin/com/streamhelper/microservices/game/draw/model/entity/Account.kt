package com.streamhelper.microservices.game.draw.model.entity

import com.streamhelper.microservices.game.draw.model.entity.embeddable.Email
import com.streamhelper.microservices.game.draw.model.entity.mapped.PrimaryKeyEntity
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.springframework.security.core.authority.SimpleGrantedAuthority

/**
 * 메시지 서버를 사용하는 유저. 즉 다른 서버입니다.
 */
@Entity
class Account(
    email: String,
    password: String,
    name: String,
    authorities: Collection<String> = listOf(),
    detail: AccountDetail? = null,
    settings: AccountSettings? = null
) : PrimaryKeyEntity() {

    /**
     * 사용자 이메일. 중복 이메일을 허용하지 않도록 합니다.
     */
    @Embedded
    var email: Email = Email(email)

    /**
     * 사용자 이메일
     */
    @Column(nullable = false)
    var password: String = password

    /**
     * 사용자 이름
     */
    @Column(nullable = false)
    var name: String = name

    @OneToOne(optional = true)
    @JoinColumn(name = "accountDetailId")
    val detail: AccountDetail? = detail

    @OneToOne(optional = true)
    @JoinColumn(name = "accountSettingsId")
    val settings: AccountSettings? = settings

    /**
     * 사용자가 삭제되면 권한도 삭제되어야 한다. [CascadeType.ALL]
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "account")
    @BatchSize(size = 10)
    protected val mutableAuthorities: MutableCollection<AccountAuthority> =
        authorities.map { AccountAuthority(it, this) }?.toMutableList() ?: mutableListOf()

    val authorities get() = mutableAuthorities.toList()

    val grantedAuthorities get() = mutableAuthorities.map { SimpleGrantedAuthority(it.authority) }

    ///////////////////////////////////////////////////////////////////////////
    // Functions
    ///////////////////////////////////////////////////////////////////////////

    fun addAuthority(authority: String) = addAuthority(AccountAuthority(authority, this))

    fun addAuthority(authority: AccountAuthority) = mutableAuthorities.add(authority)

    fun addAuthorities(authorities: Collection<String>) =
        addUserAuthorities(authorities.map { AccountAuthority(it, this) })

    fun addSimpleGrantedAuthorities(authorities: Collection<SimpleGrantedAuthority>) =
        addUserAuthorities(authorities.map { AccountAuthority(it.authority, this) })

    fun addUserAuthorities(authorities: Collection<AccountAuthority>) = mutableAuthorities.addAll(authorities)

}