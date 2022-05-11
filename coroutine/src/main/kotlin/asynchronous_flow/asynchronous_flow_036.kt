package me.hyuni.asynchronousFlow036

import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    (1..5).asFlow().collect {
        if (it == 3) cancel()
        println(it)
    }
}
