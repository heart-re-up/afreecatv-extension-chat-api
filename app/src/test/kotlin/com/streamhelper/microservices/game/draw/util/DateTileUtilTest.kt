package com.streamhelper.microservices.game.draw.util

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.ZoneId
import java.time.ZonedDateTime

class DateTileUtilTest {
    @Test
    @DisplayName("Duration 으로 미래 시간 계산 테스트")
    fun testCompute() {
        val now = ZonedDateTime.now(ZoneId.of("UTC"))
//        println(TimeZone.getDefault())
//        println("---")
        println(now)
//        println(now.zone)
//        println(now.toInstant())
//        println(now.withFixedOffsetZone())
//        println(Date.from(now.toInstant()))
//        println("---")
//        println(now.withZoneSameLocal(ZoneId.of("UTC")))
//        println(now.withZoneSameInstant(ZoneId.of("UTC")))
    }
}