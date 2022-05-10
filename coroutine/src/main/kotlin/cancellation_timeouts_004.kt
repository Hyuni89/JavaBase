package me.hyuni.cancellationAndTimeouts004

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job = launch {
        try {
            repeat(1000) {
                println("job: I'm sleeping $it ...")
                delay(500L)
            }
        } finally {
            println("job: I'm running finally")
        }
    }

    delay(1300L)
    println("main: I'm tired of waiting")
    job.cancelAndJoin()
    println("main: Now I can quit")
}
