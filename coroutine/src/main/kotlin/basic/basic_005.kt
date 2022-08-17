package me.hyuni.basic005

import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job = launch {
        delay(1000L)
        println("World")
    }

    println("Hello")
    job.join()
    println("Done")
}
