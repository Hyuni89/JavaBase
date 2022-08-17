package me.hyuni.channels009

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class Ball(var hits: Int)

suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) {
        ball.hits++
        println("$name $ball")
        delay(300)
        table.send(ball)
    }
}

fun main() = runBlocking {
    val table = Channel<Ball>()
    launch { player("Ping", table) }
    launch { player("Pong", table) }
    table.send(Ball(0))
    delay(1000)
    coroutineContext.cancelChildren()
}
