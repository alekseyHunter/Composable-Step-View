package com.customviews.stepview.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class StepItem(
    val id: Int,
    val name: String,
    val description: String,
    val mark: String,
    val indicator: StepIndicator,
    val isVisibleSubStepIndicator: Boolean,
    val subSteps: List<SubStepItem>,
    val enabled: Boolean
)

data class SubStepItem(
    val name: String,
    val description: String,
    val mark: String
)

sealed class StepIndicator(
    open val contentColor: Color,
    open val backgroundColor: Color,
    open val lineColor: Color,
    open val borderColor: Color,
    open val disabledContentColor: Color,
    open val disabledBackgroundColor: Color,
    open val disabledLineColor: Color,
    open val disabledBorderColor: Color
) {
    data class Number(
        val value: String,
        override val contentColor: Color = Color.Unspecified,
        override val backgroundColor: Color = Color.Unspecified,
        override val lineColor: Color = Color.Unspecified,
        override val borderColor: Color = Color.Unspecified,
        override val disabledContentColor: Color = Color.Unspecified,
        override val disabledBackgroundColor: Color = Color.Unspecified,
        override val disabledLineColor: Color = Color.Unspecified,
        override val disabledBorderColor: Color = Color.Unspecified
    ) : StepIndicator(
        contentColor,
        backgroundColor,
        lineColor,
        borderColor,
        disabledContentColor,
        disabledBackgroundColor,
        disabledLineColor,
        disabledBorderColor
    )

    data class Icon(
        @DrawableRes val icon: Int,
        override val contentColor: Color = Color.Unspecified,
        override val backgroundColor: Color = Color.Unspecified,
        override val lineColor: Color = Color.Unspecified,
        override val borderColor: Color = Color.Unspecified,
        override val disabledContentColor: Color = Color.Unspecified,
        override val disabledBackgroundColor: Color = Color.Unspecified,
        override val disabledLineColor: Color = Color.Unspecified,
        override val disabledBorderColor: Color = Color.Unspecified
    ) : StepIndicator(
        contentColor,
        backgroundColor,
        lineColor,
        borderColor,
        disabledContentColor,
        disabledBackgroundColor,
        disabledLineColor,
        disabledBorderColor
    )
}