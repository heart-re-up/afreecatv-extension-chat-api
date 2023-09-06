package com.streamhelper.microservices.game.draw.util

import com.tinteccnc.util.MyLogger
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * 프로듀서/컨슈머 구조의 [Job] FIFO 처리기 입니다.
 * [submit] 으로 [Job] 을 추가하고, [Channel.receive] 를 통해서 지속적으로 [Job] 을 처리합니다.
 */
class JobQueue : MyLogger {

    private val scope = GlobalScope
    private val queue = Channel<Job>(Channel.UNLIMITED)

    init {
        scope.launch {
            while (isActive) {
                queue.receive().join()
            }
        }
    }

    fun submit(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) {
        scope.launch(context, CoroutineStart.LAZY, block).also { queue.trySend(it) }
    }

    fun cancel() {
        queue.cancel()
        scope.cancel()
    }

}