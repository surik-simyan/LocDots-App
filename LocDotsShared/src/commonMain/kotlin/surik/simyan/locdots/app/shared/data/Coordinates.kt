package surik.simyan.locdots.app.shared.data

import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
    val latitude: Double,
    val longitude: Double
)