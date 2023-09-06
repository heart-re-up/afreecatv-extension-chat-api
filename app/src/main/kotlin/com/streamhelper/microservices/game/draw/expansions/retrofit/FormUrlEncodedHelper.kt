package com.streamhelper.microservices.game.draw.expansions.retrofit

object FormUrlEncodedHelper {
    fun toFieldMap(data: Any) = data.asSequencePair().toMap()
}