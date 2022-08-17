package me.hyuni.selectExpression005

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

fun CoroutineScope.switchMapDeferreds(input: ReceiveChannel<Deferred<String>>) = produce<String> {
    var current = input.receive()
    while (isActive) {
        val next = select<Deferred<String>?> {
            input.onReceiveCatching {
                it.getOrNull()
            }
            current.onAwait {
                send(it)
                input.receiveCatching().getOrNull()
            }
        }
        if (next == null) {
            println("Channel was closed")
            break
        } else {
            current = next
        }
    }
}

fun CoroutineScope.asyncString(str: String, time: Long) = async {
    delay(time)
    str
}

fun main() = runBlocking {
    val chan = Channel<Deferred<String>>()
    launch {
        for (s in switchMapDeferreds(chan)) {
            println(s)
        }
    }
    chan.send(asyncString("BEGIN", 100))
    delay(200)
    chan.send(asyncString("Slow", 500))
    delay(100)
    chan.send(asyncString("Replace", 100))
    delay(500)
    chan.send(asyncString("END", 500))
    delay(1000)
    chan.close()
    delay(500)
}
