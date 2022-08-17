package me.hyuni.asynchronousFlow005

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    (1..3).asFlow().collect { println(it) }
}
