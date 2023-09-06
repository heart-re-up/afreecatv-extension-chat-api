package com.streamhelper.microservices.game.draw.model.business.security

object AccountRoles {
    val USER = AccountRole("USER")
    val BROADCASTER = AccountRole("BROADCASTER")
    val ADMIN = AccountRole("ADMIN")
    val SUPER_USER = AccountRole("SU")
}

data class AccountRole(val role: String)
