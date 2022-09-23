package com.customviews.stepview.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class StepItem(
    val id: Int,
    val name: String,
    val description: String,
    val mark: String,
    @DrawableRes val icon: Int,
    val isActive: Boolean,
    val subSteps: List<SubStepItem>,
    val color: Color
)

data class SubStepItem(
    val name: String,
    val description: String,
    val mark: String
)