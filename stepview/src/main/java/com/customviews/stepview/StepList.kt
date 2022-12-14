package com.customviews.stepview

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.customviews.stepview.models.StepIndicator
import com.customviews.stepview.models.StepItem
import com.customviews.stepview.utils.changeLightness

@Composable
fun StepList(
    modifier: Modifier = Modifier,
    itemList: List<StepItem>,
    itemTitleStyle: TextStyle,
    itemDescriptionStyle: TextStyle,
    itemMarkStyle: TextStyle,
    colors: StepListColors = StepListDefault.stepListColors(),
    scrollState: ScrollState = rememberScrollState(),
) {

    val itemHeightList = remember {
        MutableList(itemList.size) { 0 }.toMutableStateList()
    }

    val itemColorLineList = itemList.map { item ->
        if (item.enabled)
            item.indicator.lineColor.takeOrElse {
                colors.stepIndicatorLineColor(true).value
            }
        else
            item.indicator.disabledLineColor.takeOrElse {
                colors.stepIndicatorLineColor(false).value
            }
    }

    Column(modifier = modifier
        .verticalScroll(scrollState)
        .drawBehind {
            var previousOffsetY = 8.dp.toPx()
            val offsetX = 20.dp.toPx()
            itemHeightList.forEachIndexed { index, height ->
                if (index != itemHeightList.lastIndex) {
                    val lineColor =
                        itemColorLineList[index]
                    drawLine(
                        lineColor,
                        start = Offset(offsetX, previousOffsetY),
                        end = Offset(offsetX, previousOffsetY + height.toFloat() + 8.dp.toPx()),
                        strokeWidth = 2.dp.toPx()
                    )
                    previousOffsetY += height.toFloat()
                }
            }
        }) {
        itemList.forEachIndexed { index, item ->
            StepItem(
                item = item,
                itemTitleStyle = itemTitleStyle,
                itemDescriptionStyle = itemDescriptionStyle,
                itemMarkStyle = itemMarkStyle,
                colors = colors
            ) {
                itemHeightList[index] = it
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun StepItem(
    item: StepItem,
    itemTitleStyle: TextStyle,
    itemDescriptionStyle: TextStyle,
    itemMarkStyle: TextStyle,
    colors: StepListColors,
    onChangeHeight: (Int) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    val angle: Float by animateFloatAsState(if (isExpanded) 180f else 0f)

    val indicatorBackgroundColor = if (item.enabled) item.indicator.backgroundColor.takeOrElse {
        colors.stepIndicatorBackgroundColor(item.enabled).value
    } else item.indicator.disabledBackgroundColor.takeOrElse {
        colors.stepIndicatorBackgroundColor(item.enabled).value
    }


    val indicatorContentColor = if (item.enabled) item.indicator.contentColor.takeOrElse {
        colors.stepIndicatorColor(item.enabled).value
    } else item.indicator.disabledContentColor.takeOrElse {
        colors.stepIndicatorColor(item.enabled).value
    }

    val indicatorBorderColor = if (item.enabled) item.indicator.borderColor.takeOrElse {
        colors.stepIndicatorBorderColor(item.enabled).value
    } else item.indicator.disabledBorderColor.takeOrElse {
        colors.stepIndicatorBorderColor(item.enabled).value
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged {
                onChangeHeight.invoke(it.height)
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .size(24.dp)
                    .border(2.dp, indicatorBorderColor, RoundedCornerShape(100))
                    .background(
                        indicatorBackgroundColor, RoundedCornerShape(100)
                    )
                    .clip(RoundedCornerShape(100)),
                contentAlignment = Alignment.Center
            ) {
                when (item.indicator) {
                    is StepIndicator.Icon -> {
                        Icon(
                            painter = painterResource(id = item.indicator.icon),
                            contentDescription = "",
                            modifier = Modifier.size(16.dp),
                            tint = indicatorContentColor
                        )
                    }
                    is StepIndicator.Number -> {
                        Text(
                            text = item.indicator.value,
                            style = itemMarkStyle,
                            color = indicatorContentColor
                        )
                    }
                }
            }


            BoxWithConstraints(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = item.name,
                        style = itemTitleStyle,
                        color = colors.titleColor().value,
                        modifier = Modifier.widthIn(0.dp, this@BoxWithConstraints.maxWidth - 46.dp)
                    )

                    if (item.subSteps.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .defaultMinSize(20.dp)
                                .background(Color.Gray.copy(0.05f), RoundedCornerShape(100))
                                .clip(RoundedCornerShape(100))
                                .clickable {
                                    isExpanded = !isExpanded
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_down),
                                contentDescription = "",
                                modifier = Modifier
                                    .requiredSize(18.dp)
                                    .rotate(angle),
                                tint = Color.Black.copy(0.7f)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }

            if (item.subSteps.isEmpty()) {
                Text(
                    text = item.mark,
                    style = itemMarkStyle,
                    color = colors.markColor().value,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        AnimatedContent(
            targetState = isExpanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    // Expand horizontally first.
                                    IntSize(targetSize.width, initialSize.height) at 150
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    // Shrink vertically first.
                                    IntSize(initialSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }
            }
        ) { targetExpanded ->
            if (targetExpanded) {
                Column {
                    item.subSteps.groupBy { it.name }.forEach {
                        Text(
                            text = it.key,
                            style = itemDescriptionStyle,
                            modifier = Modifier.padding(start = 48.dp, top = 4.dp, bottom = 4.dp),
                            color = colors.titleColor().value,
                        )
                        it.value.forEach {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                if (item.subSteps.isNotEmpty() && item.isVisibleSubStepIndicator) {
                                    Box(
                                        modifier = Modifier
                                            .padding(horizontal = 14.dp)
                                            .size(10.dp)
                                            .background(
                                                indicatorContentColor,
                                                RoundedCornerShape(100)
                                            )
                                            .border(
                                                2.dp,
                                                indicatorBorderColor,
                                                RoundedCornerShape(100)
                                            )
                                    )

                                    Text(
                                        text = it.description,
                                        style = itemDescriptionStyle,
                                        color = colors.descriptionColor().value,
                                        modifier = Modifier.weight(1f),
                                    )
                                } else {
                                    Text(
                                        text = it.description,
                                        style = itemDescriptionStyle,
                                        modifier = Modifier.padding(
                                            start = 48.dp,
                                            top = 4.dp,
                                            bottom = 4.dp
                                        ),
                                        color = colors.descriptionColor().value,
                                    )
                                }

                                Text(
                                    text = it.mark,
                                    style = itemMarkStyle,
                                    color = colors.markColor().value,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (item.subSteps.isNotEmpty() && item.isVisibleSubStepIndicator) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 14.dp)
                                .size(10.dp)
                                .background(
                                    indicatorContentColor,
                                    RoundedCornerShape(100)
                                )
                                .border(2.dp, indicatorBorderColor, RoundedCornerShape(100))
                        )

                        Text(
                            text = item.description,
                            style = itemDescriptionStyle,
                            color = colors.descriptionColor().value,
                            modifier = Modifier.weight(1f),
                        )

                        Text(
                            text = item.mark,
                            style = itemMarkStyle,
                            color = colors.markColor().value,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    } else {
                        Text(
                            text = item.description,
                            style = itemDescriptionStyle,
                            modifier = Modifier
                                .padding(start = 48.dp)
                                .weight(1f),
                            color = colors.descriptionColor().value,
                        )

                        if (item.subSteps.isNotEmpty()) {
                            Text(
                                text = item.mark,
                                style = itemMarkStyle,
                                color = colors.markColor().value,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Stable
interface StepListColors {

    @Composable
    fun titleColor(): State<Color>

    @Composable
    fun descriptionColor(): State<Color>

    @Composable
    fun markColor(): State<Color>

    @Composable
    fun stepIndicatorColor(enabled: Boolean): State<Color>

    @Composable
    fun stepIndicatorBackgroundColor(enabled: Boolean): State<Color>

    @Composable
    fun stepIndicatorLineColor(enabled: Boolean): State<Color>

    @Composable
    fun stepIndicatorBorderColor(enabled: Boolean): State<Color>
}

@Immutable
private class DefaultStepListColors(
    private val titleColor: Color,
    private val descriptionColor: Color,
    private val markColor: Color,
    private val stepIndicatorColor: Color,
    private val stepIndicatorBackgroundColor: Color,
    private val stepIndicatorLineColor: Color,
    private val stepIndicatorBorderColor: Color,
    private val disabledStepIndicatorColor: Color,
    private val disabledStepIndicatorBackgroundColor: Color,
    private val disabledStepIndicatorLineColor: Color,
    private val disabledStepIndicatorBorderColor: Color
) : StepListColors {
    @Composable
    override fun titleColor(): State<Color> {
        return rememberUpdatedState(titleColor)
    }

    @Composable
    override fun descriptionColor(): State<Color> {
        return rememberUpdatedState(descriptionColor)
    }

    @Composable
    override fun markColor(): State<Color> {
        return rememberUpdatedState(markColor)
    }

    @Composable
    override fun stepIndicatorColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) stepIndicatorColor else disabledStepIndicatorColor)
    }

    @Composable
    override fun stepIndicatorBackgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) stepIndicatorBackgroundColor else disabledStepIndicatorBackgroundColor)
    }

    @Composable
    override fun stepIndicatorLineColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) stepIndicatorLineColor else disabledStepIndicatorLineColor)
    }

    @Composable
    override fun stepIndicatorBorderColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) stepIndicatorBorderColor else disabledStepIndicatorBorderColor)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DefaultStepListColors

        if (titleColor != other.titleColor) return false
        if (descriptionColor != other.descriptionColor) return false
        if (markColor != other.markColor) return false
        if (stepIndicatorColor != other.stepIndicatorColor) return false
        if (stepIndicatorBackgroundColor != other.stepIndicatorBackgroundColor) return false
        if (stepIndicatorLineColor != other.stepIndicatorLineColor) return false
        if (stepIndicatorBorderColor != other.stepIndicatorBorderColor) return false
        if (disabledStepIndicatorColor != other.disabledStepIndicatorColor) return false
        if (disabledStepIndicatorBackgroundColor != other.disabledStepIndicatorBackgroundColor) return false
        if (disabledStepIndicatorLineColor != other.disabledStepIndicatorLineColor) return false
        if (disabledStepIndicatorBorderColor != other.disabledStepIndicatorBorderColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = titleColor.hashCode()
        result = 31 * result + descriptionColor.hashCode()
        result = 31 * result + markColor.hashCode()
        result = 31 * result + stepIndicatorColor.hashCode()
        result = 31 * result + stepIndicatorBackgroundColor.hashCode()
        result = 31 * result + stepIndicatorLineColor.hashCode()
        result = 31 * result + stepIndicatorBorderColor.hashCode()
        result = 31 * result + disabledStepIndicatorColor.hashCode()
        result = 31 * result + disabledStepIndicatorBackgroundColor.hashCode()
        result = 31 * result + disabledStepIndicatorLineColor.hashCode()
        result = 31 * result + disabledStepIndicatorBorderColor.hashCode()
        return result
    }
}

object StepListDefault {

    @Composable
    fun stepListColors(
        titleColor: Color = Color.Black,
        descriptionColor: Color = Color.Gray,
        markColor: Color = Color.Gray,
        stepIndicatorColor: Color = Color.Black,
        stepIndicatorBackgroundColor: Color = Color.Black.changeLightness(0.9f),
        stepIndicatorLineColor: Color = Color.Black,
        stepIndicatorBorderColor: Color = Color.White,
        disabledStepIndicatorColor: Color = Color.Gray,
        disabledStepIndicatorBackgroundColor: Color = Color.Gray.changeLightness(0.9f),
        disabledStepIndicatorLineColor: Color = Color.Gray,
        disabledStepIndicatorBorderColor: Color = Color.White
    ): StepListColors = DefaultStepListColors(
        titleColor = titleColor,
        descriptionColor = descriptionColor,
        markColor = markColor,
        stepIndicatorColor = stepIndicatorColor,
        stepIndicatorBackgroundColor = stepIndicatorBackgroundColor,
        stepIndicatorLineColor = stepIndicatorLineColor,
        stepIndicatorBorderColor = stepIndicatorBorderColor,
        disabledStepIndicatorColor = disabledStepIndicatorColor,
        disabledStepIndicatorBackgroundColor = disabledStepIndicatorBackgroundColor,
        disabledStepIndicatorLineColor = disabledStepIndicatorLineColor,
        disabledStepIndicatorBorderColor = disabledStepIndicatorBorderColor,
    )
}