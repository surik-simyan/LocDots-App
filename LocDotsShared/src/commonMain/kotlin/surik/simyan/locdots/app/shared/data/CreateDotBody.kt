package surik.simyan.locdots.app.shared.data

import kotlinx.serialization.Serializable

@Serializable
data class CreateDotBody(
    val userId: String,
    val message: String,
    val coordinates: Coordinates
)