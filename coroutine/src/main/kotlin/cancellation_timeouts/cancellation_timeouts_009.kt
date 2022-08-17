package me.hyuni.cancellationAndTimeouts009

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

var acquire = 0

class Resource {
    init { acquire++ }
    fun close() { acquire-- }
}

fun main() {
    runBlocking {
        repeat(100_000) {
            launch {
                var resource: Resource? = null
                try {
                    withTimeout(60) {
                        delay(50)
                        resource = Resource()
                    }
                } finally {
                    resource?.close()
                }
            }
        }
    }

    println(acquire)
}
