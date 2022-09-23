package com.customviews.stepview

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.customviews.sample.R
import com.customviews.stepview.models.StepItem
import com.customviews.stepview.models.StepItemType
import com.customviews.stepview.models.SubStepItem
import com.customviews.stepview.utils.changeLightness

@Composable
fun StepView(
    modifier: Modifier = Modifier,
    itemList: List<StepItem>,
    itemTitleStyle: TextStyle,
    itemDescriptionStyle: TextStyle
) {
    val scrollState = rememberScrollState()

    val itemHeightList = remember {
        MutableList(itemList.size) { 0 }.toMutableStateList()
    }

    Row(modifier = modifier.verticalScroll(scrollState)) {
        Column(modifier = Modifier.drawBehind {
            var previousOffsetY = 8.dp.toPx()
            val offsetX = 20.dp.toPx()
            itemHeightList.forEachIndexed { index, height ->
                if (index != itemHeightList.lastIndex) {
                    drawLine(
                        itemList[index].color,
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
                    itemTitleStyle,
                    itemDescriptionStyle,
                    itemType = when (index) {
                        itemList.lastIndex -> StepItemType.Last
                        0 -> StepItemType.First
                        else -> StepItemType.Another
                    }
                ) {
                    itemHeightList[index] = it
                }
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
    itemType: StepItemType,
    onChangeHeight: (Int) -> Unit,
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    val iconBackgroundColor by remember {
        mutableStateOf(item.color.changeLightness(0.9f))
    }

    val angle: Float by animateFloatAsState(if (isExpanded) 180f else 0f)

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
                    .border(2.dp, Color.White, RoundedCornerShape(100))
                    .background(
                        iconBackgroundColor, RoundedCornerShape(100)
                    )
                    .clip(RoundedCornerShape(100)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = "",
                    modifier = Modifier.size(16.dp),
                    tint = item.color
                )
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item.name, style = itemTitleStyle)
                if (itemType == StepItemType.Another) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
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
                                .size(18.dp)
                                .rotate(angle),
                            tint = Color.Black.copy(0.7f)
                        )
                    }
                }
            }

            if (!isExpanded) {
                Text(
                    text = item.mark,
                    style = itemDescriptionStyle,
                    color = Color.Gray.copy(0.9f),
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
                            modifier = Modifier.padding(start = 48.dp, top = 4.dp, bottom = 4.dp)
                        )
                        it.value.forEach {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .size(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .border(2.dp, Color.White, RoundedCornerShape(100))
                                            .background(item.color, RoundedCornerShape(100))
                                    )
                                }

                                Text(
                                    text = it.description,
                                    style = itemDescriptionStyle,
                                    color = Color.Gray.copy(0.9f),
                                    modifier = Modifier.weight(1f)
                                )

                                Text(
                                    text = it.mark,
                                    style = itemDescriptionStyle,
                                    color = Color.Gray.copy(0.9f),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (itemType == StepItemType.Another) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .size(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .border(2.dp, Color.White, RoundedCornerShape(100))
                                    .background(item.color, RoundedCornerShape(100))
                            )
                        }
                        Text(
                            text = item.description,
                            style = itemDescriptionStyle,
                        )
                    } else {
                        Text(
                            text = item.description,
                            style = itemDescriptionStyle,
                            modifier = Modifier.padding(start = 48.dp, top = 4.dp, bottom = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowPreviewOrderList() {
    MaterialTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            StepView(
                Modifier.fillMaxSize(),
                listOf(
                    StepItem(
                        0,
                        "Создан",
                        "Санкт-Петербург",
                        "13.03.2022",
                        R.drawable.ic_home_work,
                        false,
                        emptyList(),
                        color = Color(0xFFCDBC54)
                    ),
                    StepItem(
                        0,
                        "В пути",
                        "Екатеренбург / Отправлен в пункт выдачи",
                        "14.03.2022",
                        R.drawable.ic_car,
                        false,
                        listOf(
                            SubStepItem("Санкт-Петербург", "Принят на доставку", "13.03.2022"),
                            SubStepItem(
                                "Санкт-Петербург",
                                "Отправлен в г. Екатеренбург",
                                "13.03.2022"
                            ),
                            SubStepItem("Екатеренбург", "Отправлен в пункт выдачи", "14.03.2022"),
                        ),
                        Color(0xFFCB9B5B)
                    ),
                    StepItem(
                        0,
                        "Готов к выдаче",
                        "Челябинск / Поступил. Заберите заказ",
                        "15.03.2022",
                        R.drawable.ic_person,
                        false,
                        listOf(SubStepItem("Челябинск", "Поступил. Заберите заказ", "15.03.2022")),
                        Color(0xFF3B9681)
                    ),
                    StepItem(
                        0,
                        "Вручен",
                        "Челябинск",
                        "15.03.2022",
                        R.drawable.ic_home,
                        false,
                        emptyList(),
                        Color(0xFF3B9681)
                    )
                ),
                itemDescriptionStyle = TextStyle(fontSize = 12.sp),
                itemTitleStyle = TextStyle.Default
            )
        }
    }
}