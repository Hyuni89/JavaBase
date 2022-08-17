package me.hyuni.asynchronousFlow037

import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    (1..5).asFlow().cancellable().collect {
        if (it == 3) cancel()
        println(it)
    }
}
