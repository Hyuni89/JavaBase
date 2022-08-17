package me.hyuni.asynchronousFlow026

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

fun simple(): Flow<String> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i)
    }
}.map {
    check(it <= 1) { "Crashed on $it" }
    "String $it"
}

fun main() = runBlocking {
    simple()
        .catch { emit("Caught $it") }
        .collect { println(it) }
}
