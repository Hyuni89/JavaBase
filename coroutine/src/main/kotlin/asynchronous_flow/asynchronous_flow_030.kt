package me.hyuni.asynchronousFlow030

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.runBlocking

fun simple(): Flow<Int> = (1..3).asFlow()

fun main() = runBlocking {
    simple()
        .onCompletion { println("Done") }
        .collect { println(it) }
}
