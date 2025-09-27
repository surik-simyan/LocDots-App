package surik.simyan.locdots.app.shared.domain.usecases

import surik.simyan.locdots.app.shared.base.MultiplatformResult
import surik.simyan.locdots.app.shared.data.DotDto
import surik.simyan.locdots.app.shared.data.DotSort
import surik.simyan.locdots.app.shared.domain.mappers.toDomain
import surik.simyan.locdots.app.shared.domain.model.Dot
import surik.simyan.locdots.app.shared.network.ApiResponse
import surik.simyan.locdots.app.shared.network.DotsApiService

class GetAllDotsUseCaseImpl(private val api: DotsApiService) : GetAllDotsUseCase {

    override suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        sortingType: DotSort
    ): MultiplatformResult<List<Dot>> {
        return try {
            val response: ApiResponse<List<DotDto>> =
                api.getAllDots(latitude, longitude, sortingType)
            when (response) {
                is ApiResponse.Success -> MultiplatformResult.Success(response.data.map { it.toDomain() })
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
