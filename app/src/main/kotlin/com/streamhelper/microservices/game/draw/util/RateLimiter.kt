package com.streamhelper.microservices.game.draw.util

import com.tinteccnc.util.MyLogger
import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Refill
import io.github.bucket4j.local.SynchronizationStrategy
import kotlinx.coroutines.delay
import java.time.Duration
import java.util.concurrent.TimeUnit

/**
 * 단순히 시간당 실행 횟수의 제한만 처리하는 리미터 입니다.
 * [suspend] 함수인 [attempt] 함수를 호출해서 리미터의 기능을 이용합니다.
 * [attempt] 함수는 실행 횟수 제한이 걸릴 때, 현재 CoroutineContext 를 [suspend] 합니다.
 *
 * - 시간당 실행 제한이 걸리지 않았다면 즉시 액션이 실행됩니다.
 * - 제한이 걸린 경우 호출가능한 시점까지 지연 후 처리합니다.
 */
open class RateLimiter(tokens: Long, duration: Duration) : MyLogger {

    private val refill by lazy { Refill.greedy(tokens, duration) }
    private val bandwidth by lazy { Bandwidth.classic(tokens, refill) }
    private val bucket by lazy {
        Bucket.builder()
            .addLimit(bandwidth)
            .withNanosecondPrecision()
            .withSynchronizationStrategy(SynchronizationStrategy.LOCK_FREE)
            .build()
    }

    open fun before() {
        // do nothing.
    }

    open fun after() {
        // do nothing.
    }

    private fun performSequentially(action: () -> Unit) {
        before()
        action()
        after()
    }

    suspend fun attempt(action: () -> Unit) {
        val probe = bucket.tryConsumeAndReturnRemaining(1)
        if (probe.isConsumed) performSequentially(action)
        else {
            // ns 를 ms 로 정확하게 변환하면, 실제로 충전되지 않은 시간이 나올 수 있다. 1ms 를 더해서 실제로 충전이 완료된 이후 시점을 얻는다.
            val waitInMillis = TimeUnit.NANOSECONDS.toMillis(probe.nanosToWaitForRefill) + 1
            info("wait for refill($waitInMillis ms)")
            delay(waitInMillis)
            attempt(action) // 재귀호출
        }
    }
}
