package me.hyuni.main001

import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        delay(1000L)
        println("World!")
    }

    println("Hello")
}
