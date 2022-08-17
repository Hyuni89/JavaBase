package me.hyuni.asynchronousFlow034

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun events(): Flow<Int> = (1..3).asFlow().onEach { delay(1000) }

fun main() = runBlocking {
    events()
        .onEach { println("Event: $it") }
        .launchIn(this)
    println("Done")
}
