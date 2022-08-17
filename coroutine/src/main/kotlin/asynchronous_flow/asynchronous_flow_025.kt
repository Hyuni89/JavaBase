package me.hyuni.asynchronousFlow025

import kotlinx.coroutines.flow.Flow
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
    try {
        simple().collect {
            println(it)
        }
    } catch (e: Throwable) {
        println("Caught $e")
    }
}
