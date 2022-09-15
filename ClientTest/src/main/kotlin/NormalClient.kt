import java.lang.management.ManagementFactory
import java.lang.management.OperatingSystemMXBean
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun main() {
    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder(URI("http://localhost:8080/delay")).build()

    val start = System.currentTimeMillis()
    repeat(10000) {
        client.send(request, HttpResponse.BodyHandlers.ofString())
    }
    println("total time took ${System.currentTimeMillis() - start} ms")
}
