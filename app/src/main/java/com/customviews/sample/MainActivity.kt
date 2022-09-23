package com.customviews.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.customviews.sample.ui.theme.CustomViewsTheme
import com.customviews.stepview.StepView
import com.customviews.stepview.models.StepItem
import com.customviews.stepview.models.SubStepItem
import com.customviews.sample.R

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
        }
    }
}