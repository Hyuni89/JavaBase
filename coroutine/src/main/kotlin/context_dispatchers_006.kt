package me.hyuni.contextAndDispatchers006

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking {
    val request = launch {
        launch(Job()) {
            log("job1: I run in my own Job and execute independently!")
            delay(1000)
            log("job1: I am not affected by cancellation of the request")
        }

        launch {
            delay(100)
            log("job2: I am a child of the request coroutine")
            delay(1000)
            log("job2: I will not execute this line if my parent request is cancelled")
        }
    }

    delay(500)
    log("main: cancel request")
    request.cancel()
    delay(1000)
    log("main: Who has survived request cancellation?")
}
