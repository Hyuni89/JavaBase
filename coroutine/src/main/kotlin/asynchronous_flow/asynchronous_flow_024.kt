package me.hyuni.asynchronousFlow024

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking {
    try {
        simple().collect {
            println(it)
            check(it <= 1) { "Collected $it" }
        }
    } catch (e: Throwable) {
        println("Caught $e")
    }
}
