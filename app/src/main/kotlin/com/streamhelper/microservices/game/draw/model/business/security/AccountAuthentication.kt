package com.streamhelper.microservices.game.draw.model.business.security

import com.streamhelper.microservices.game.draw.model.entity.Account
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

/**
 * 스프링 시큐리티에서 사용하는 Authentication 을 위한 구현체입니다.
 * [Account] 정보를 기반으로 하는 Authentication 을 생성합니다.
 */
class AccountAuthentication(
    principal: AccountPrincipal,
    val password: String,
    authorities: Collection<GrantedAuthority>
) : UsernamePasswordAuthenticationToken(principal, password, authorities) {

    constructor(account: Account) : this(
        AccountPrincipal(account.id),
        account.password,
        account.grantedAuthorities
    )
    
}