package me.hyuni.asynchronousFlow006

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

suspend fun performRequest(request: Int): String {
    delay(1000)
    return "response $request"
}

fun main() = runBlocking {
    (1..3).asFlow()
        .map { performRequest(it) }
        .collect { println(it) }
}
