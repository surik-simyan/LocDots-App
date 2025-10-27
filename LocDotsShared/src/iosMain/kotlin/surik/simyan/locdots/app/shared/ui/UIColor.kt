package surik.simyan.locdots.app.shared.ui

import platform.UIKit.UIColor

val DavyGray = fromHexToColor(DAVY_GRAY_HEX)
val Platinum = fromHexToColor(PLATINUM_HEX)
val Gray = fromHexToColor(GRAY_HEX)
val EerieBlack = fromHexToColor(EERIE_BLACK_HEX)
val Jet = fromHexToColor(JET_HEX)

fun fromHexToColor(hex: Long): UIColor {
    val red = ((hex shr 16) and 0xFF) / 255.0
    val green = ((hex shr 8) and 0xFF) / 255.0
    val blue = (hex and 0xFF) / 255.0
    val alpha = ((hex shr 24) and 0xFF) / 255.0

    return UIColor(
        red = red,
        green = green,
        blue = blue,
        alpha = alpha,
    )
}
