package com.streamhelper.microservices.game.draw.expansions.spring

import com.streamhelper.microservices.game.draw.model.business.security.AccountAuthentication
import com.streamhelper.microservices.game.draw.model.business.security.AccountPrincipal
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

val authentication get() = SecurityContextHolder.getContext().authentication as AccountAuthentication
val principal get() = authentication.principal as AccountPrincipal
val accountAuthorities get() = authentication.authorities.toList()

fun Collection<GrantedAuthority>.hasAuthority(grantedAuthority: GrantedAuthority) = any { it == grantedAuthority }

fun Collection<GrantedAuthority>.hasAuthority(authority: String) = any { it.authority == authority }

fun Collection<GrantedAuthority>.hasAnyAuthority(vararg grantedAuthorities: GrantedAuthority) =
    this.any { a -> grantedAuthorities.any { a == it } }

fun Collection<GrantedAuthority>.hasAnyAuthority(vararg authorities: String) =
    this.any { authorities.contains(it.authority) }

fun Collection<GrantedAuthority>.hasAllAuthority(vararg grantedAuthorities: GrantedAuthority) =
    this == grantedAuthorities.toList()

fun Collection<GrantedAuthority>.hasAllAuthority(vararg authorities: String) =
    map { it.authority } == authorities.toList()

