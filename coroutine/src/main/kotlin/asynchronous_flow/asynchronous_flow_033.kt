package me.hyuni.asynchronousFlow033

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

fun events(): Flow<Int> = (1..3).asFlow().onEach { delay(1000) }

fun main() = runBlocking {
    events()
        .onEach { println("Event: $it") }
        .collect()
    println("Done")
}
