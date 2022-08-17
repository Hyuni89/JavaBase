package me.hyuni.selectExpression004

import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select
import kotlin.random.Random

fun CoroutineScope.asyncString(time: Int) = async {
    delay(time.toLong())
    "Waited for $time ms"
}

fun CoroutineScope.asyncStringList(): List<Deferred<String>> {
    val random = Random(3)
    return List(12) { asyncString(random.nextInt(1000)) }
}

fun main() = runBlocking {
    val list = asyncStringList()
    val result = select<String> {
        list.withIndex().forEach { (index, deferred) ->
            deferred.onAwait {
                "Deferred $index produced answer '$it'"
            }
        }
    }
    println(result)
    val countActive = list.count { it.isActive }
    println("$countActive coroutines are still active")
}
