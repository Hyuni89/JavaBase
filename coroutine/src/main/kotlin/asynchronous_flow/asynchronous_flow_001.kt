package me.hyuni.asynchronousFlow001

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

suspend fun simple(): List<Int> {
    delay(1000)
    return listOf(1, 2, 3)
}

fun main() = runBlocking {
    simple().forEach { println(it) }
}
