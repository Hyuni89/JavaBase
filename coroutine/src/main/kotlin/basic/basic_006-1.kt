package me.hyuni.basic006

import kotlin.concurrent.thread

fun main() {
    repeat(100_000) {
        thread {
            Thread.sleep(5000L)
            print(".")
        }
    }
}
