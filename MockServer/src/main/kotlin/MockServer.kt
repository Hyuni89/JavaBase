package me.hyuni

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class MockServer

fun main(args: Array<String>) {
    runApplication<MockServer>(*args)
}
