package surik.simyan.locdots.app.shared

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.math.*

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