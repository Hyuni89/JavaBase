package me.hyuni.asynchronousFlow002

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(1000)
        emit(i)
    }
}

fun main() = runBlocking {
    launch {
        for (k in 1..3) {
            println("I'm not blocked $k")
            delay(1000)
        }
    }

    simple().collect { println(it) }
}
