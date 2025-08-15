package surik.simyan.locdots.app.shared.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import surik.simyan.locdots.BuildKonfig
import surik.simyan.locdots.app.shared.data.Dot
import surik.simyan.locdots.app.shared.data.DotSort

class DotsApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    fun getAllDots(latitude: Double, longitude: Double, sortingType: DotSort): Flow<List<Dot>> = flow {
        val dotsList = httpClient.get("${BuildKonfig.API_URL}/dots") {
            url {
                parameters.append("latitude", latitude.toString())
                parameters.append("longitude", longitude.toString())
//                parameters.append("sortingType", sortingType.value)
            }
        }.body<List<Dot>>()
        emit(dotsList)
    }

    fun createNewDot(

    ): Flow<HttpResponse> = flow {
        val response = httpClient.post("${BuildKonfig.API_URL}/dots") {
            contentType(ContentType.Application.Json)
        }
        emit(response)
    }
}