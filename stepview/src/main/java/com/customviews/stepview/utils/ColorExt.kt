package com.customviews.stepview.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

fun Color.changeLightness(lightness: Float): Color {
    val colorHSL = floatArrayOf(0f, 0f, 0f)
    ColorUtils.RGBToHSL(
        this.toArgb().red,
        this.toArgb().green,
        this.toArgb().blue,
        colorHSL
    )
    return Color.hsl(
        colorHSL[0],
        colorHSL[1],
        lightness
    )
}