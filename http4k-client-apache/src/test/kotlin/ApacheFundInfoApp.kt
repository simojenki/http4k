import org.http4k.client.ApacheClient
import org.http4k.core.BodyMode
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status

fun execute(name : String, http : HttpHandler) {
    val url = "https://api.fundinfo.com/"

    val response = http(Request(GET, url))

    println("GET $url using $name returned ${response.status}")
    if(response.status != Status.OK) throw RuntimeException("Got ${response.status}")

    if(response.bodyString().matches(".*error.*".toRegex(setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.IGNORE_CASE)))) {
        println("!!! Body for $name contains an error")
    } else {
        println("Body for $name contains no error")
    }
}

fun main(args: Array<String>) {
    execute("BodyMode.Memory", ApacheClient(requestBodyMode = BodyMode.Memory))

    execute("BodyMode.Stream", ApacheClient(requestBodyMode = BodyMode.Stream))

    println("done..")
}