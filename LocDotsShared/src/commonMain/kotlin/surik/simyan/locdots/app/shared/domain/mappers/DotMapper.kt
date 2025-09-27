package surik.simyan.locdots.app.shared.domain.mappers

import surik.simyan.locdots.app.shared.data.DotDto
import surik.simyan.locdots.app.shared.domain.model.Dot
import surik.simyan.locdots.app.shared.getDateFromDateTime

fun DotDto.toDomain(): Dot = Dot(
    id = id,
    message = message,
    formattedDate = getDateFromDateTime(dateTime)
)
