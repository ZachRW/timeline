import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException
import java.net.URL

object ResourceHandler {
    fun getResource(path: String): URL = javaClass.getResource(path)
        ?: throw FileNotFoundException("Cannot find file at $path")
}

fun main() {
    println("Reading timeline.json")
    val timelineJson = ResourceHandler.getResource("timeline.json").readText()
    val timelineData = Json.decodeFromString<TimelineData>(timelineJson)

    embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
            anyHost()
        }
        install(Compression) {
            gzip()
        }

        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            static("/") {
                resources("")
            }

            route(TimelineData.path) {
                get {
                    call.respond(timelineData)
                }
            }
        }
    }.start(wait = true)
}
