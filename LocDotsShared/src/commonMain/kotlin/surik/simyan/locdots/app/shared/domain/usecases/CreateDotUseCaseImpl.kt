package surik.simyan.locdots.app.shared.domain.usecases

import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import surik.simyan.locdots.app.shared.base.MultiplatformResult
import surik.simyan.locdots.app.shared.getDeviceId
import surik.simyan.locdots.app.shared.network.ApiResponse
import surik.simyan.locdots.app.shared.network.DotsApiService

class CreateDotUseCaseImpl(
    private val api: DotsApiService,
    private val geolocator: Geolocator,
) : CreateDotUseCase {

    override suspend operator fun invoke(
        message: String,
    ): MultiplatformResult<Unit> {
        when (val result = geolocator.current(Priority.HighAccuracy)) {
            is GeolocatorResult.Success -> {
                return try {
                    val response: ApiResponse<Unit> =
                        api.createDot(
                            userId = getDeviceId(),
                            message = message,
                            latitude = result.data.coordinates.latitude,
                            longitude = result.data.coordinates.longitude,
                        )
                    when (response) {
                        is ApiResponse.Success -> MultiplatformResult.Success(response.data)
                        is ApiResponse.Error -> MultiplatformResult.Error(
                            message = response.error.message,
                            cause = response.error.code,
                        )
                    }
                } catch (e: Exception) {
                    MultiplatformResult.Error(message = e.message ?: "Unknown error")
                }
            }

            is GeolocatorResult.Error -> return when (result) {
                is GeolocatorResult.PermissionDenied -> MultiplatformResult.Error(
                    message = "Location permission denied. Please enable it in settings to use this feature.",
                )

                is GeolocatorResult.GeolocationFailed -> MultiplatformResult.Error(
                    message = "Failed to determine your location. Please try again.",
                )

                is GeolocatorResult.NotFound -> MultiplatformResult.Error(
                    message = "Could not find your location. Make sure GPS is turned on.",
                )

                else -> MultiplatformResult.Error(
                    message = "An unknown location error occurred.",
                )
            }
        }
    }
}
