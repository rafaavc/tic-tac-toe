import ai.MCTSRobot
import ai.Robot
import model.GameModel
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.respondHtml
import io.ktor.http.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.html.*

fun HTML.index() {
    head {
        title("TicTacToe")
    }
    body {
        div {
            id = "root"
        }
        script(src = "/static/TicTacToe.js") {}
    }
}

fun main() {
    val robot: Robot = MCTSRobot()

    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            anyHost()
        }
        install(Compression) {
            gzip()
        }

        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
            post("/play") {
                val gameModel = call.receive<GameModel>()
                val response = robot.getNextPlay(gameModel)

                call.respond(response)
            }
            static("/static") {
                resources()
            }
        }
    }.start(wait = true)
}
