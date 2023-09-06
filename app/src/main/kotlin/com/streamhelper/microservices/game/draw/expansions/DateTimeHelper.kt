package com.streamhelper.microservices.game.draw.expansions

import java.sql.Date
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

fun ZonedDateTime.toDate() = Date.from(toInstant())
fun LocalDateTime.toDate() = Date.from(toInstant(ZoneOffset.UTC))

/**
 * 현재 시간을 사용하는 블럭 DSL 함수입니다.
 */
fun now(now: ZonedDateTime? = ZonedDateTime.now(), usingNow: ((now: ZonedDateTime) -> Unit)) {
    ZonedDateTime.now().apply(usingNow)
}