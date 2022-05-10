package me.hyuni.contextAndDispatchers003

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking {
    val a = async {
        log("I'm computing a piece of the answer")
        6
    }

    val b = async {
        log("I'm computing another piece of the answer")
        7
    }

    log("The answer is ${a.await() + b.await()}")
}
