package com.customviews.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.customviews.app.R
import com.customviews.sample.ui.theme.CustomViewsTheme
import com.customviews.stepview.StepList
import com.customviews.stepview.StepListColors
import com.customviews.stepview.StepListDefault
import com.customviews.stepview.models.StepIndicator
import com.customviews.stepview.models.StepItem
import com.customviews.stepview.models.SubStepItem
import com.customviews.stepview.utils.changeLightness

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomViewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    List()
                }
            }
        }
    }
}

@Preview
@Composable
fun List() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        StepList(
            Modifier,
            listOf(
                StepItem(
                    0,
                    "Created",
                    "CHINA",
                    "13.03.2022",
                    StepIndicator.Icon(R.drawable.ic_home_work),
                    true,
                    emptyList()
                ),
                StepItem(
                    0,
                    "Out for delivery",
                    "CHINA / Arrived at the Post office",
                    "14.03.2022",
                    StepIndicator.Icon(R.drawable.ic_car),
                    true,
                    listOf(
                        SubStepItem("Shatian Town", "Ready for dispatch", "13.03.2022"),
                        SubStepItem(
                            "Shatian Town",
                            "Outbound in sorting center",
                            "13.03.2022"
                        ),
                        SubStepItem("CHINA", "Arrived at the Post office", "14.03.2022"),
                    )
                ),
                StepItem(
                    0,
                    "Available for pickup at the Post Office",
                    "CHINA / Available for pickup at the Post Office",
                    "15.03.2022",
                    StepIndicator.Icon(R.drawable.ic_person),
                    true,
                    listOf(
                        SubStepItem(
                            "CHINA",
                            "Available for pickup at the Post Office",
                            "15.03.2022"
                        )
                    ),
                ),
                StepItem(
                    0,
                    "Delivered",
                    "CHINA",
                    "15.03.2022",
                    StepIndicator.Icon(R.drawable.ic_home),
                    true,
                    emptyList()
                )
            ),
            itemTitleStyle = TextStyle.Default,
            itemDescriptionStyle = TextStyle(fontSize = 12.sp),
            itemMarkStyle = TextStyle(fontSize = 12.sp)
        )
/*
        StepList(
            Modifier,
            listOf(
                StepItem(
                    0,
                    "Создан",
                    "Санкт-Петербург",
                    "13.03.2022",
                    StepIndicator.Number(
                        "0",
                        Color(0xFFCDBC54),
                        Color(0xFFCDBC54).changeLightness(0.9f),
                        Color(0xFFCDBC54)
                    ),
                    false,
                    emptyList()
                ),
                StepItem(
                    0,
                    "В пути",
                    "Екатеренбург / Отправлен в пункт выдачи",
                    "14.03.2022",
                    StepIndicator.Number(
                        "1",
                        Color(0xFFCB9B5B),
                        Color(0xFFCB9B5B).changeLightness(0.9f),
                        Color(0xFFCB9B5B)
                    ),
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
                ),
                StepItem(
                    0,
                    "Готов к выдаче",
                    "Челябинск / Поступил. Заберите заказ",
                    "15.03.2022",
                    StepIndicator.Number(
                        "2",
                        Color(0xFF3B9681),
                        Color(0xFF3B9681).changeLightness(0.9f),
                        Color(0xFF3B9681)
                    ),
                    false,
                    listOf(SubStepItem("Челябинск", "Поступил. Заберите заказ", "15.03.2022")),
                ),
                StepItem(
                    0,
                    "Вручен",
                    "Челябинск",
                    "15.03.2022",
                    StepIndicator.Number(
                        "3",
                        Color(0xFF53926D),
                        Color(0xFF53926D).changeLightness(0.9f),
                        Color(0xFF53926D)
                    ),
                    false,
                    emptyList(),
                )
            ),
            itemTitleStyle = TextStyle.Default,
            itemDescriptionStyle = TextStyle(fontSize = 12.sp),
            itemMarkStyle = TextStyle(fontSize = 12.sp),
            colors = StepListDefault.stepListColors(
                descriptionColor = Color.Gray,

            )
        )*/
    }
}