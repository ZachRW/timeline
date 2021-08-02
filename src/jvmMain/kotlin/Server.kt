import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.io.FileNotFoundException
import java.net.URL

object ResourceHandler {
    fun getResource(path: String): URL = javaClass.getResource(path)
        ?: throw FileNotFoundException("Cannot find file at $path")
}

fun main() {
    embeddedServer(Netty, 8080) {
        install(CORS) {
            method(HttpMethod.Get)
            anyHost()
        }

        routing {
            route("/") {
                get {
                    call.respondText(
                        ResourceHandler.getResource("index.html").readText(),
                        ContentType.Text.Html
                    )
                }
                static {
                    resources("")
                    resources("data")
                }
            }
        }
    }.start(wait = true)
}
