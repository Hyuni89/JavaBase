package me.hyuni.asynchronousFlow035

import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun foo(): Flow<Int> = flow {
    for (i in 1..5) {
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking {
    foo().collect {
        if (it == 3) cancel()
        println(it)
    }
}
