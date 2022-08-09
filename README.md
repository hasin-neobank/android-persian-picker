# Persian Date Picker & Alphabet Picker
Android library providing a beautiful persian date picker and alphabet picker to use inside modal bottom sheet for Jetpack Compose.

[![](https://jitpack.io/v/hasin-neobank/android-persian-picker.svg)](https://jitpack.io/#hasin-neobank/android-persian-picker)
![License MIT](https://img.shields.io/badge/MIT-9E9F9F?style=flat-square&label=License)
![Android minimuml version](https://img.shields.io/badge/21+-9E9F9F?style=flat-square&label=Minimum&logo=android)


Download
--------
Add in project build.gradle:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

// App build.gradle
dependencies {
    implementation 'com.github.hasin-neobank:android-persian-picker:latestVersion'

}
```

## Usage
--------
Create an instance from `PersianDatePicker` or `PersianAlphabetPicker` outside of composable function (the best place is ViewModel):

```kotlin
//date picker
val picker = PersianDatePicker()
```
```kotlin
//alphabet picker
val picker = PersianAlphabetPicker()
```
Set `picker.DatePickerUI` or `picker.AlphabetPickerUI` as content to `ModalBottomSheetLayout`:

```kotlin
picker.DatePickerUI(
    buttonTextStyle = MaterialTheme.typography.h3,
    selectedTextStyle = MaterialTheme.typography.h3.copy(
        color = TextBlack,
        fontWeight = FontWeight.Normal
    ),
    unSelectedTextStyle = MaterialTheme.typography.h3.copy(
        color = TextMediumGray,
        fontWeight = FontWeight.Normal
    ),
    buttonText = "تایید",
    onButtonPressed = { date ->
        result= "current persian date: ${date.persianYear}/${date.persianMonth}/${date.persianDay}"
    },
)
```

```kotlin
picker.AlphabetPickerUI(
    buttonTextStyle = MaterialTheme.typography.h3,
    selectedTextStyle = MaterialTheme.typography.h3.copy(
        color = TextBlack,
        fontWeight = FontWeight.Normal
    ),
    unSelectedTextStyle = MaterialTheme.typography.h3.copy(
        color = TextMediumGray,
        fontWeight = FontWeight.Normal
    ),
    buttonText = "تایید",
    onButtonPressed = { alphabet ->
        result= "current persian date: $alphabet"
    },
)
```

<img src="https://user-images.githubusercontent.com/67331684/182156346-47f02adb-b968-47db-bee7-5e0f96c80152.jpg" width="300"> <img src="https://user-images.githubusercontent.com/67331684/182156523-80404675-cb03-47f6-9a29-c06ec47cd4c4.jpg" width="300">

**Note:** see [Example](app/src/main/java/com/example/picker/MainActivity.kt) for more detail.

## Credits
* Special Thanks to [PersianDatePicker](https://github.com/alibehzadian/PersianDatePicker).
* Special Thanks to [PersianDatePickerDialog](https://github.com/aliab/Persian-Date-Picker-Dialog).
* Special Thanks to [ComposeNumberPicker](https://github.com/ChargeMap/Compose-NumberPicker).

## License
```
   
The MIT License (MIT)

Copyright (c) 2022 Hasin Neo Bank

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```