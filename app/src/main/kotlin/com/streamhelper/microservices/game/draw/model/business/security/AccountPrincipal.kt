package com.streamhelper.microservices.game.draw.model.business.security

import com.streamhelper.microservices.game.draw.model.entity.Wallet
import java.security.Principal
import java.util.*

/**
 * 스프링 시큐리티에서 사용하는 계정 주체입니다.
 * 계정 주체는 단순히 계정이 누구인지만 나타냅니다.
 *
 * 이 정보는 토큰에서 얻을 수 있는 정보로 구성될 수 있어야 합니다.
 * 즉, 개인정보를 제외한 정보로만 구성할 수 있습니다.
 */
data class AccountPrincipal(
    val accountId: UUID,
) : Principal {

    constructor(account: Wallet) : this(account.id)

    /**
     * [accountId] 의 문자열 입니다.
     */
    override fun getName(): String = accountId.toString()

}