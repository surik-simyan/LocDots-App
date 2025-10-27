package surik.simyan.locdots.app.shared.domain.usecases

import surik.simyan.locdots.app.shared.base.MultiplatformResult
import surik.simyan.locdots.app.shared.data.DotSort
import surik.simyan.locdots.app.shared.domain.model.Dot

interface GetAllDotsUseCase {
    suspend operator fun invoke(
        sortingType: DotSort,
    ): MultiplatformResult<List<Dot>>
}
