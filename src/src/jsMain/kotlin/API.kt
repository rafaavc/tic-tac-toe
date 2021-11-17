import model.GameModel
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.browser.window
import model.utilities.Position

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getMachinePlay(gameModel: GameModel): Position {
    return jsonClient.post("${window.location.origin}/play") {
        contentType(ContentType.Application.Json)
        body = gameModel
    }
}
