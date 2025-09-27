package surik.simyan.locdots.app.shared.domain.usecases

import surik.simyan.locdots.app.shared.base.MultiplatformResult

interface CreateDotUseCase {
    suspend operator fun invoke(
        message: String,
        latitude: Double,
        longitude: Double
    ): MultiplatformResult<Unit>
}
