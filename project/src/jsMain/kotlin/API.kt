import model.GameModel
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.browser.window
import model.utilities.Position
import kotlin.math.floor

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getMachinePlay(gameModel: GameModel, time: Double): Position {
    return jsonClient.post("${window.location.origin}/play?time=${floor(time*1000).toInt()}") {
        contentType(ContentType.Application.Json)
        body = gameModel
    }
}
