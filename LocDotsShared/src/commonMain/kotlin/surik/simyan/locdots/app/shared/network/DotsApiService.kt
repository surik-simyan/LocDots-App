package surik.simyan.locdots.app.shared.network

import surik.simyan.locdots.app.shared.data.DotDto
import surik.simyan.locdots.app.shared.data.DotSort

interface DotsApiService {
    suspend fun getAllDots(
        latitude: Double,
        longitude: Double,
        sortingType: DotSort
    ): ApiResponse<List<DotDto>>

    suspend fun createDot(
        userId: String,
        message: String,
        latitude: Double,
        longitude: Double
    ): ApiResponse<Unit>
}
