package me.hyuni.contextAndDispatchers011

import kotlinx.coroutines.*

val threadLocal = ThreadLocal<String?>()

fun main() = runBlocking {
    threadLocal.set("main")
    println("Pre-main, current thread: ${Thread.currentThread().name}, thread local value: ${threadLocal.get()}")
    val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
        println("Launch start, current thread: ${Thread.currentThread().name}, thread local value: ${threadLocal.get()}")
        yield()
        println("After yield, current thread: ${Thread.currentThread().name}, thread local value: ${threadLocal.get()}")
    }
    job.join()
    println("Post-main, current thread: ${Thread.currentThread().name}, thread local value: ${threadLocal.get()}")
}
