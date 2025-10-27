package surik.simyan.locdots.app.shared.data

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class DotDto(
    val id: String,
    val message: String,
    val coordinates: Coordinates,
    val dateTime: LocalDateTime,
    val distance: Double,
)
