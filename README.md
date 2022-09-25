# Composable Step View

## Examples

<table>
 <tr>
  <td>Basic</td><td>Custom</td>
 </tr>
 <tr>
  <td><img src="docs/steplist/Basic.png" width="75%"></td><td><img src="docs/steplist/Custom.png" width="75%"></td>
 </tr>
</table>

## Usage
Simple step list:

``` kotlin
StepList(
    modifier = Modifier,
    itemList = listOf(
        StepItem(
            id = 0,
            name = "Created",
            description = "CHINA",
            mark = "13.03.2022",
            indicator = StepIndicator.Icon(R.drawable.ic_home_work),
            isVisibleSubStepIndicator = true,
            subSteps = emptyList()
        ), 
        StepItem(
            id = 1,
            name = "Out for delivery",
            description = "CHINA / Arrived at the Post office",
            mark = "14.03.2022",
            indicator = StepIndicator.Icon(R.drawable.ic_car),
            isVisibleSubStepIndicator = true,
            subSteps = listOf(
                SubStepItem(
                    name = "Shatian Town", 
                    description = "Ready for dispatch", 
                    mark = "13.03.2022"),
                SubStepItem(
                     name = "Shatian Town", 
                     description = "Outbound in sorting center", 
                     mark = "13.03.2022"
                ),
                SubStepItem(
                    name = "CHINA", 
                    description = "Arrived at the Post office", 
                    mark = "14.03.2022"
                ),
            )
        )
    ),
    itemTitleStyle = TextStyle.Default,
    itemDescriptionStyle = TextStyle(fontSize = 12.sp),
    itemMarkStyle = TextStyle(fontSize = 12.sp)
)
```

Step indicator styling:

``` kotlin
StepIndicator.Icon(
    icon = R.drawable.ic_car, 
    contentColor = Color.Black,
    backgroundColor = Color.Black.changeLightness(0.9f),
    lineColor = Color.Black,
    borderColor = Color.Black
)

StepIndicator.Number(
    value = "1", 
    contentColor = Color.Black,
    backgroundColor = Color.Black.changeLightness(0.9f),
    lineColor = Color.Black,
    borderColor = Color.Black
)
```

Color scheme styling:

``` kotlin
StepList(
    ...
    colors = StepListDefault.stepListColors(
        titleColor = Color.Black,
        descriptionColor = Color.Gray,
        markColor = Color.Gray,
        stepIndicatorColor = Color.Black,
        stepIndicatorBackgroundColor = Color.Black.changeLightness(0.9f),
        stepIndicatorLineColor = Color.Black,
        stepIndicatorBorderColor = Color.White
    ),
    ...
)
```

For examples, refer to the [samples](https://github.com/alekseyHunter/customviews/blob/dev_0/app/src/main/java/com/customviews/sample/MainActivity.kt).

## Download

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation "com.github.alekseyHunter:customviews:<version>"
}
```

## License

    Copyright 2022 Aleksey Okhotnichenko.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.