import org.http4k.client.ApacheClient
import org.http4k.core.BodyMode
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status

fun main(args: Array<String>) {
    val http = ApacheClient(requestBodyMode = BodyMode.Stream)
    val response = http(Request(GET, "https://api.fundinfo.com/"))

    if(response.status != Status.OK) throw RuntimeException("Got ${response.status}")

    if(response.bodyString().matches(".*error.*".toRegex(setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.IGNORE_CASE)))) {
        throw RuntimeException("Body contains error string")
    }

    println("done..")
}