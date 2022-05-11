package me.hyuni.cancellationAndTimeouts008

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
                val resource = withTimeout(60) {
                    delay(50)
                    Resource()
                }

                resource.close()
            }
        }
    }

    println(acquire)
}
