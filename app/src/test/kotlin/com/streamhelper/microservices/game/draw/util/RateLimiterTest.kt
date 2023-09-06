package com.streamhelper.microservices.game.draw.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class RateLimiterTest {

    @Test
    fun testRateLimiter() = runTest {
//        var count = 0
//        var success = 0
//        var failure = 0
//        val limiter = RateLimiter(null)
////        runBlocking {
////
////        }
//        runBlocking {
////            var delayMillis = 0L
////            var prevState = false
//            while (count++ < 500) {
//                limiter.perform {
//                    println("[$count]")
//                }
////                println("delay: $delayMillis")
////                delay(delayMillis + 1)
//            }
//        }
//        println("count: $count")
//        println("success: $success")
//        println("failure: $failure")
//        Assertions.assertThat(success).isEqualTo(50)
//        Assertions.assertThat(failure).isEqualTo(50)
//        deferred.await()
//        advanceUntilIdle()
    }

}