package me.hyuni.contextAndDispatchers010

import kotlinx.coroutines.*

class Activity {
    private val mainScope = CoroutineScope(Dispatchers.Default)

    fun destory() {
        mainScope.cancel()
    }

    fun doSomething() {
        repeat(10) {
            mainScope.launch {
                delay((it + 1) * 200L)
                println("Coroutine $it is done")
            }
        }
    }
}

fun main() = runBlocking {
    val activity = Activity()
    activity.doSomething()
    println("Launched coroutines")
    delay(500L)
    println("Destroying activity!")
    activity.destory()
    delay(1000)
}
