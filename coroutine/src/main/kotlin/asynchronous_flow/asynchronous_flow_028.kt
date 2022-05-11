package me.hyuni.asynchronousFlow028

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking {
    simple()
        .onEach {
            check(it <= 1) { "Collected $it" }
            println(it)
        }
        .catch { println("Caught $it") }
        .collect()
}
