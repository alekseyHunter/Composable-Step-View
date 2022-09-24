# Composable custom views

## Usage

1. Add `maven { url 'https://jitpack.io' }` to repositories block in your gradle file.
2. Add `implementation 'com.github.alekseyHunter:customviews:0.0.3'` to your dependencies.
3. Add `StepList` into your Composable-function.

## Examples

<table>
 <tr>
  <td>Basic</td><td>Custom</td>
 </tr>
 <tr>
  <td><img src="docs/steplist/Basic.png" width="75%"></td><td><img src="docs/steplist/Custom.png" width="75%"></td>
 </tr>
</table>

## Docs

``` kotlin
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
            1,
            "Out for delivery",
            "CHINA / Arrived at the Post office",
            14.03.2022",
            StepIndicator.Icon(R.drawable.ic_car),
            true,
            listOf(
                SubStepItem("Shatian Town", "Ready for dispatch", "13.03.2022"),
                SubStepItem(
                     "Shatian Town", "Outbound in sorting center", "13.03.2022"
                ),
                SubStepItem(
                    "CHINA", "Arrived at the Post office", "14.03.2022"
                ),
            )
        )
    ),
    itemTitleStyle = TextStyle.Default,
    itemDescriptionStyle = TextStyle(fontSize = 12.sp),
    itemMarkStyle = TextStyle(fontSize = 12.sp)
)
```

For examples, refer to the [samples](https://github.com/alekseyHunter/customviews/blob/dev_0/app/src/main/java/com/customviews/sample/MainActivity.kt).

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