package com.streamhelper.microservices.game.draw.model.ro

data class RoLogon(
    val accessToken: String,
    val refreshToken: String,
    val isAdministrator: Boolean
) {
    constructor(roToken: RoToken, isAdministrator: Boolean) : this(
        roToken.accessToken,
        roToken.refreshToken,
        isAdministrator
    )
}
