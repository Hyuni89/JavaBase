package me.hyuni.asynchronousFlow032

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.runBlocking

fun simple(): Flow<Int> = (1..3).asFlow()

fun main() = runBlocking {
    simple()
        .onCompletion { cause -> println("Flow completed with $cause") }
        .collect {
            check(it <= 1) { "Collected $it" }
            println(it)
        }
}
