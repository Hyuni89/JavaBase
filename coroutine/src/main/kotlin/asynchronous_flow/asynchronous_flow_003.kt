package me.hyuni.asynchronousFlow003

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun simple(): Flow<Int> = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}

fun main() = runBlocking {
    println("Calling simple function")
    val flow = simple()
    println("Calling collect")
    flow.collect { println(it) }
    println("Calling collect again")
    flow.collect { println(it) }
}
