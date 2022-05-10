package me.hyuni.contextAndDispatchers005

import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("My job is ${coroutineContext[Job]}")
}
