package surik.simyan.locdots.app.shared.ui

import platform.UIKit.UIColor

val DavyGray = fromHexToColor(DavyGrayHex)
val Platinum = fromHexToColor(PlatinumHex)
val Gray = fromHexToColor(GrayHex)
val EerieBlack = fromHexToColor(EerieBlackHex)
val Jet = fromHexToColor(JetHex)

fun fromHexToColor(hex: Long): UIColor {
    val red = ((hex shr 16) and 0xFF) / 255.0
    val green = ((hex shr 8) and 0xFF) / 255.0
    val blue = (hex and 0xFF) / 255.0
    val alpha = ((hex shr 24) and 0xFF) / 255.0

    return UIColor(
        red = red,
        green = green,
        blue = blue,
        alpha = alpha
    )
}