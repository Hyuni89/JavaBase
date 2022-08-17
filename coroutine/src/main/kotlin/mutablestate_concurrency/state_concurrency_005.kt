package me.hyuni.stateAndConcurrency005

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun massiveRun(action: suspend() -> Unit) {
    val n = 100
    val k = 1000
    val time = measureTimeMillis {
        coroutineScope {
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}

val counterContext = newSingleThreadContext("CounterContext")
var counter = 0

fun main() = runBlocking {
    withContext(counterContext) {
        massiveRun {
            counter++
        }
    }
    println("Counter == $counter")
}
