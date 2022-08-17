package me.hyuni.asynchronousFlow014

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}

fun main() = runBlocking {
    val time = measureTimeMillis {
        simple().collect {
            delay(300)
            println(it)
        }
    }
    println("Collected in $time ms")
}
