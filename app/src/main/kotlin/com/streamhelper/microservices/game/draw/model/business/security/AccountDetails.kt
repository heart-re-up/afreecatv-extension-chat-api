package com.streamhelper.microservices.game.draw.model.business.security

import com.streamhelper.microservices.game.draw.model.entity.Account
import org.springframework.security.core.userdetails.User

class AccountDetails(account: Account) : User(account.name, account.password, account.grantedAuthorities) {
    val account: Account = account
}