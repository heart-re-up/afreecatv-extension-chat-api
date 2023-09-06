package com.streamhelper.microservices.game.draw.util

import com.streamhelper.microservices.game.draw.expansions.toDate
import java.time.Duration
import java.time.ZonedDateTime
import java.util.*

object DateTimeUtil {

    /**
     * 기준시간 [standard] 로 부터 [duration] 만큼 떨어진 시간을 계산해서, [Date] 로 반환합니다.
     * [standard] 가 제공되지 않으면, 현재 시간을 기준으로 합니다.
     * [duration] 이 제공되지 않으면, [duration] 을 계산하지 않습니다.
     *
     * @param standard 계산을 시작할 기준 시간 입니다.
     * @param duration 기준 시간으로 부터 계산되는 기간입니다.
     */
    fun computeExpiration(standard: ZonedDateTime? = null, duration: Duration? = null): Date {
        var t = standard ?: ZonedDateTime.now()
        if (duration != null) t += duration
        return t.toDate()
    }

}