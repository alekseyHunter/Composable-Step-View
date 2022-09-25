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

        BasicStepList()

        CustomStepList()
    }
}

@Preview(showBackground = true)
@Composable
private fun BasicStepList() {
    StepList(
        modifier = Modifier,
        itemList = listOf(
            StepItem(
                id =  0,
                name = "Created",
                description = "CHINA",
                mark =  "13.03.2022",
                indicator = StepIndicator.Icon(R.drawable.ic_home_work),
                isVisibleSubStepIndicator = true,
                subSteps = emptyList()
            ),
            StepItem(
                id = 0,
                name = "Out for delivery",
                description = "CHINA / Arrived at the Post office",
                mark = "14.03.2022",
                indicator = StepIndicator.Icon(R.drawable.ic_car),
                isVisibleSubStepIndicator = true,
                subSteps = listOf(
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
                id = 0,
                name = "Available for pickup at the Post Office for pickup at the Post Office",
                description = "CHINA / Available for pickup at the Post Office",
                mark = "15.03.2022",
                indicator = StepIndicator.Icon(R.drawable.ic_person),
                isVisibleSubStepIndicator = true,
                subSteps = listOf(
                    SubStepItem(
                        "CHINA",
                        "Available for pickup at the Post Office",
                        "15.03.2022"
                    )
                ),
            ),
            StepItem(
                id = 0,
                name = "Delivered",
                description = "CHINA",
                mark = "15.03.2022",
                indicator = StepIndicator.Icon(R.drawable.ic_home),
                isVisibleSubStepIndicator = true,
                subSteps = emptyList()
            )
        ),
        itemTitleStyle = TextStyle.Default,
        itemDescriptionStyle = TextStyle(fontSize = 12.sp),
        itemMarkStyle = TextStyle(fontSize = 12.sp)
    )
}

@Preview(showBackground = true)
@Composable
private fun CustomStepList() {
    StepList(
        modifier = Modifier,
        itemList = listOf(
            StepItem(
                id = 0,
                name = "Created",
                description = "CHINA",
                mark = "13.03.2022",
                indicator = StepIndicator.Number(
                    value = "0",
                    contentColor = Color(0xFFCDBC54),
                    backgroundColor = Color(0xFFCDBC54).changeLightness(0.9f),
                    lineColor = Color(0xFFCDBC54)
                ),
                isVisibleSubStepIndicator = true,
                subSteps = emptyList()
            ),
            StepItem(
                id = 0,
                name = "Out for delivery",
                description = "CHINA / Arrived at the Post office",
                mark = "14.03.2022",
                indicator = StepIndicator.Number(
                    value = "1",
                    contentColor = Color(0xFFCB9B5B),
                    backgroundColor = Color(0xFFCB9B5B).changeLightness(0.9f),
                    lineColor = Color(0xFFCB9B5B)
                ),
                isVisibleSubStepIndicator = true,
                subSteps = listOf(
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
                id = 0,
                name = "Available for pickup at the Post Office",
                description = "CHINA / Available for pickup at the Post Office",
                mark = "15.03.2022",
                indicator = StepIndicator.Number(
                    value = "2",
                    contentColor = Color(0xFF3B9681),
                    backgroundColor = Color(0xFF3B9681).changeLightness(0.9f),
                    lineColor = Color(0xFF3B9681)
                ),
                isVisibleSubStepIndicator = true,
                subSteps = listOf(
                    SubStepItem(
                        "CHINA",
                        "Available for pickup at the Post Office",
                        "15.03.2022"
                    )
                ),
            ),
            StepItem(
                id = 0,
                name = "Delivered",
                description = "CHINA",
                mark = "15.03.2022",
                indicator = StepIndicator.Number(
                    value = "3",
                    contentColor = Color(0xFF53926D),
                    backgroundColor = Color(0xFF53926D).changeLightness(0.9f),
                    lineColor = Color(0xFF53926D)
                ),
                isVisibleSubStepIndicator = true,
                subSteps = emptyList()
            )
        ),
        itemTitleStyle = TextStyle.Default,
        itemDescriptionStyle = TextStyle(fontSize = 12.sp),
        itemMarkStyle = TextStyle(fontSize = 12.sp),
        colors = StepListDefault.stepListColors()
    )
}