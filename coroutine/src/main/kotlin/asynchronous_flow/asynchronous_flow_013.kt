package me.hyuni.asynchronousFlow013

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        Thread.sleep(1000)
        log("Emitting $i")
        emit(i)
    }
}.flowOn(Dispatchers.Default)

fun main() = runBlocking {
    simple().collect { log("Collected $it") }
}
