package com.streamhelper.microservices.game.draw.model.ro

import com.streamhelper.microservices.game.draw.model.entity.Account

data class RoAccount(
    val id: String,
    val name: String,
    val email: String
) {
    constructor(account: Account) : this(account.id.toString(), account.name, account.email.value)
}
