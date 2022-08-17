package me.hyuni.asynchronousFlow022

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i: First")
    delay(500)
    emit("$i: Second")
}

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    (1..3).asFlow().onEach { delay(100) }
        .flatMapMerge { requestFlow(it) }
        .collect {
            println("$it at ${System.currentTimeMillis() - startTime} ms from start")
        }
}
