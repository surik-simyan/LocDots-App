package surik.simyan.locdots.app.shared.data

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Dot(
    val id: String,
    val message: String,
    val location: Coordinates,
    val dateTime: LocalDateTime,
)