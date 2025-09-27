package surik.simyan.locdots.app.shared.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import co.touchlab.kermit.Logger as KermitLogger
import io.ktor.client.plugins.logging.Logger as KtorLogger

val client = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(Logging) {
        logger = object : KtorLogger {
            override fun log(message: String) {
                KermitLogger.i(message)
            }
        }
        level = LogLevel.ALL
    }
}

suspend inline fun <reified T> safeApiCallResponse(
    crossinline block: suspend () -> HttpResponse
): ApiResponse<T> {
    val response = block()
    val bodyString = response.bodyAsText()
    val withUnknownKeys = Json { ignoreUnknownKeys = true }

    // Manual polymorphic parsing based on "error" key
    val parsed: ApiResponse<T> = if (bodyString.contains("\"error\"")) {
        withUnknownKeys.decodeFromString<ApiResponse.Error>(bodyString)
    } else {
        withUnknownKeys.decodeFromString<ApiResponse.Success<T>>(bodyString)
    }

    return parsed
}
