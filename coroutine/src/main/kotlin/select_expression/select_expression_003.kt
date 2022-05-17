package me.hyuni.selectExpression003

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

fun CoroutineScope.produceNumbers(side: SendChannel<Int>) = produce<Int> {
    for (num in 1..10) {
        delay(100)
        select<Unit> {
            onSend(num) {}
            side.onSend(num) {}
        }
    }
}

fun main() = runBlocking {
    val side = Channel<Int>()
    launch {
        side.consumeEach { println("Side channel has $it") }
    }
    produceNumbers(side).consumeEach {
        println("Consuming $it")
        delay(250)
    }
    println("Done consuming")
    coroutineContext.cancelChildren()
}
