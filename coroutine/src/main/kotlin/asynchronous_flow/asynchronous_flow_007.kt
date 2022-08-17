package me.hyuni.asynchronousFlow007

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking

suspend fun performRequest(request: Int): String {
    delay(1000)
    return "response $request"
}

fun main() = runBlocking {
    (1..3).asFlow()
        .transform {
            emit("Making request $it")
            emit(performRequest(it))
        }
        .collect { println(it) }
}
