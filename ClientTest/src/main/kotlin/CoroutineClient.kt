import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun main() = runBlocking {
    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder(URI("http://localhost:8080/delay")).build()

    val start = System.currentTimeMillis()
    for (i in 2..1500 step 5) {
        val sub = mutableListOf<Deferred<*>>()
        repeat(i) {
            sub.add(async { request(client, request) })
        }
        sub.forEach { it.await() }
        Thread.sleep(3L)
        print("..$i")
    }
    println("total time took ${System.currentTimeMillis() - start} ms")
}

suspend fun request(client: HttpClient, request: HttpRequest): String {
    val res = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
    return res.await().body()
}
