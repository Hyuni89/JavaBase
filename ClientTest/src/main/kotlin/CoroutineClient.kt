import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.future.await
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {
    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder(URI("http://localhost:8080/delay")).build()
    val poolContext = newFixedThreadPoolContext(100, "coroutine")

    val start = System.currentTimeMillis()
    val sub = mutableListOf<Deferred<*>>()
    repeat(10000) {
        sub.add(async(poolContext) { request(client, request) })
    }
    sub.forEach { it.await() }
    println("total time took ${System.currentTimeMillis() - start} ms")
}

suspend fun request(client: HttpClient, request: HttpRequest): String {
    val res = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
    return res.await().body()
}
