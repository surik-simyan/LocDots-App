package surik.simyan.locdots.app.shared.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import surik.simyan.locdots.BuildKonfig
import surik.simyan.locdots.app.shared.data.Coordinates
import surik.simyan.locdots.app.shared.data.CreateDotBody
import surik.simyan.locdots.app.shared.data.DotDto
import surik.simyan.locdots.app.shared.data.DotSort

class DotsApiServiceImpl(private val client: HttpClient) : DotsApiService {

    override suspend fun getAllDots(
        latitude: Double?,
        longitude: Double?,
        sortingType: DotSort,
    ): ApiResponse<List<DotDto>> = safeApiCallResponse<List<DotDto>> {
        client.get("${BuildKonfig.API_URL}/dots") {
            url {
                parameters.append("latitude", latitude.toString())
                parameters.append("longitude", longitude.toString())
                // parameters.append("sortingType", sortingType.value)
            }
        }
    }

    override suspend fun createDot(
        userId: String,
        message: String,
        latitude: Double?,
        longitude: Double?,
    ): ApiResponse<Unit> = safeApiCallResponse {
        client.post("${BuildKonfig.API_URL}/dots") {
            contentType(ContentType.Application.Json)
            setBody(
                CreateDotBody(
                    userId = userId,
                    message = message,
                    coordinates = Coordinates(latitude, longitude),
                ),
            )
        }
    }
}
