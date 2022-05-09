package me.hyuni.main013

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main() = runBlocking {
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) {
            println("I'm sleeping $it ...")
            delay(500L)
        }
        "Done"
    }
    println("Result is $result")
}
