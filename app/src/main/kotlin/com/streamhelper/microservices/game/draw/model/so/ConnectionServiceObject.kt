package com.streamhelper.microservices.game.draw.model.so

import java.util.*

data class ConnectionServiceObject(
    val accountId: UUID,
    val deviceId: UUID,
    val screenId: UUID,
)