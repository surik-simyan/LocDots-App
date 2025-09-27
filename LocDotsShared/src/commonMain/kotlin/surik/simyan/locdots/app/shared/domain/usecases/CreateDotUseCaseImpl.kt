package surik.simyan.locdots.app.shared.domain.usecases

import surik.simyan.locdots.app.shared.base.MultiplatformResult
import surik.simyan.locdots.app.shared.getDeviceId
import surik.simyan.locdots.app.shared.network.ApiResponse
import surik.simyan.locdots.app.shared.network.DotsApiService
import kotlin.Result

class CreateDotUseCaseImpl(
    private val api: DotsApiService
) : CreateDotUseCase {

    override suspend operator fun invoke(
        message: String,
        latitude: Double,
        longitude: Double
    ): MultiplatformResult<Unit> {
        return try {
            val response: ApiResponse<Unit> =
                api.createDot(getDeviceId(), message, latitude, longitude)
            when (response) {
                is ApiResponse.Success -> MultiplatformResult.Success(response.data)
                is ApiResponse.Error -> MultiplatformResult.Error(
                    message = response.error.message,
                    cause = response.error.code
                )
            }
        } catch (e: Exception) {
            MultiplatformResult.Error(message = e.message ?: "Unknown error")
        }
    }
}
