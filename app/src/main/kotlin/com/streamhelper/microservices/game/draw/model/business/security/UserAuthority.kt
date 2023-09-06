package com.streamhelper.microservices.game.draw.model.business.security

import com.streamhelper.microservices.game.draw.model.CodeEnum

enum class UserAuthority(override val code: String) : CodeEnum<String> {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    SUPERUSER("ROLE_SUPERUSER")
}