package com.customviews.stepview.models

sealed class StepItemType{
    object First: StepItemType()
    object Last: StepItemType()
    object Another: StepItemType()
}
