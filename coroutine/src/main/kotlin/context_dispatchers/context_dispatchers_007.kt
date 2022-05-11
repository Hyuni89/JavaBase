package me.hyuni.contextAndDispatchers007

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val request = launch {
        repeat(3) {
            launch {
                delay((it + 1) * 200L)
                println("Coroutine $it is done")
            }
        }
        println("request: I'm done and I don't explicitly join my children that are still active")
    }

    request.join()
    println("Now processing of the request is complete")
}
