package me.hyuni.exceptions008

import kotlinx.coroutines.*

fun main() = runBlocking {
    try {
        supervisorScope {
            val child = launch {
                try {
                    println("The child is sleeping")
                    delay(Long.MAX_VALUE)
                } finally {
                    println("The child is cancelled")
                }
            }
            yield()
            println("Throwing an exception from the scope")
            throw AssertionError()
        }
    } catch (e: AssertionError) {
        println("Caught an assertion error")
    }
}

