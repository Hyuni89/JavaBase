import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Callable
import java.util.concurrent.Future

fun main() {
    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder(URI("http://localhost:8080/delay")).build()
    val pool = ThreadPoolTaskExecutor().apply {
        corePoolSize = 100
        maxPoolSize = 300
        initialize()
    }

    val start = System.currentTimeMillis()
    val futures = mutableListOf<Future<*>>()
    repeat(10000) {
        futures.add(pool.submit(Callable { client.send(request, HttpResponse.BodyHandlers.ofString()) }))
    }
    futures.forEach { it.get() }
    println("total time took ${System.currentTimeMillis() - start} ms")

    pool.destroy()
}
