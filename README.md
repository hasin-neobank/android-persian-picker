# Persian Date Picker & Alphabet Picker


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