package surik.simyan.locdots.app.shared

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.math.roundToInt
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getDateFromDateTime(dateTime: LocalDateTime): String {
    val now = Clock.System.now()
    val past = dateTime.toInstant(TimeZone.UTC)
    val duration = now - past
    return when {
        duration.inWholeSeconds < 60 -> "was ${duration.inWholeSeconds} seconds ago"
        duration.inWholeMinutes < 60 -> "was ${duration.inWholeMinutes} minutes ago"
        duration.inWholeHours < 24 -> "was ${duration.inWholeHours} hours ago"
        duration.inWholeDays < 7 -> "was ${duration.inWholeDays} days ago"
        duration.inWholeDays < 30 -> "was ${duration.inWholeDays / 7} weeks ago"
        duration.inWholeDays < 365 -> "was ${duration.inWholeDays / 30} months ago"
        else -> "was ${duration.inWholeDays / 365} years ago"
    }
}

fun formatDistance(distance: Double): String = when {
    distance < 2.5 -> {
        // Very close distance - creative message
        "Right here"
    }
    distance < 1000.0 -> {
        // Less than 1km - show in meters
        when {
            distance < 10 -> {
                val rounded = (distance * 10).roundToInt() / 10.0
                "$rounded m"
            }
            distance < 100 -> "${distance.roundToInt()} m"
            else -> "${distance.roundToInt()} m"
        }
    }
    else -> {
        // 1km or more - show in kilometers
        val kilometers = distance / 1000
        when {
            kilometers < 10 -> {
                val rounded = (kilometers * 100).roundToInt() / 100.0
                "$rounded km"
            }
            kilometers < 100 -> {
                val rounded = (kilometers * 10).roundToInt() / 10.0
                "$rounded km"
            }
            else -> "${kilometers.roundToInt()} km"
        }
    }
}
