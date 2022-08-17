package me.hyuni.asynchronousFlow027

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking {
    simple()
        .catch { println("Caught $it") }
        .collect {
            check(it <= 1) { "Collected $it" }
            println(it)
        }
}
