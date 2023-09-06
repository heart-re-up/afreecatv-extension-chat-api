package com.streamhelper.microservices.game.draw.model.so

import com.streamhelper.microservices.game.draw.model.ServiceObject
import com.streamhelper.microservices.game.draw.model.entity.Account
import com.streamhelper.microservices.game.draw.model.business.security.UserAuthority

data class AccountCreateServiceObject(
    val name: String,
    val email: String,
    val password: String
) : ServiceObject<Any> {
    override fun toEntity(): Account {
        return Account(email, password, name, listOf(UserAuthority.USER, UserAuthority.ADMIN).map { it.name })
    }

    override fun validate() {
//        TODO("Not yet implemented")
    }
}

