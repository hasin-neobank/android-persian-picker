package com.example.picker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import com.example.picker.api.PersianPickerDate
import com.example.picker.date.PersianDateImpl
import com.example.picker.utils.bottomSheetSymbolHeight
import com.example.picker.utils.bottomSheetSymbolRadius
import com.example.picker.utils.bottomSheetSymbolWidth
import com.example.picker.utils.inActiveInputBorderColor

class PersianDatePicker(
    preSelectedMonth: Int = 0,
    preSelectedYear: Int = 0,
    preSelectedDay: Int = 0,
    yearRange: Int = 100,
    minAge: Int = 18,
) {
    private val persianDate: PersianPickerDate = PersianDateImpl()
    private var minYear = persianDate.persianYear - (minAge) - yearRange
    private val maxYear = persianDate.persianYear - (minAge)
    private val maxMonth = persianDate.persianMonth
    private val maxDay = persianDate.persianDay
    private var selectedYear by mutableStateOf(preSelectedYear)
    private var selectedMonth by mutableStateOf(preSelectedMonth)
    var selectedDay by mutableStateOf(preSelectedDay)
    var maxSelectableYear by mutableStateOf(maxYear)
    var yearSelectableRange by
    mutableStateOf((minYear..maxSelectableYear).toList())

    var maxSelectableMonth by mutableStateOf(maxMonth)
    var monthSelectableRange
            by mutableStateOf((1..maxSelectableMonth).toList())

    var maxSelectableDay by mutableStateOf(maxDay)
    var daySelectableRange by
    mutableStateOf((1..maxSelectableDay).toList())

    init {
        persianDate.setDate(
            persianDate.persianYear - (minAge),
            persianDate.persianMonth,
            persianDate.persianDay
        )
        if (selectedYear == 0) {
            selectedYear = maxYear
        }
        if (selectedMonth == 0) {
            selectedMonth = maxMonth
        }
        if (selectedDay == 0) {
            selectedDay = maxDay
        }
    }

    //start UI code
    @Composable
    fun DatePickerUI(
        buttonTextStyle: TextStyle? = null,
        selectedTextStyle: TextStyle,
        unSelectedTextStyle: TextStyle,
        buttonText: String? = null,
        bottomSheetTopRow: @Composable () -> Unit = {},
        hasBottomSheetTopSymbol: Boolean = true,
        currentSelectedDateTextStyle: TextStyle = MaterialTheme.typography.body1,
        customButton: @Composable ((PersianPickerDate) -> Unit)? = null,
        showCurrentSelectedDate: Boolean = false,
        onButtonPressed: (persianDate: PersianPickerDate) -> Unit = {},
        selectorColor: Color = Color(0xFFECEEF1),
    ) {

        val monthNames = remember {
            listOf(
                "فروردین",
                "اردیبهشت",
                "خرداد",
                "تیر",
                "مرداد",
                "شهریور",
                "مهر",
                "آبان",
                "آذر",
                "دی",
                "بهمن",
                "اسفند"
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentHeight()
        ) {
            if (hasBottomSheetTopSymbol) {
                Box(
                    modifier = Modifier
                        .height(bottomSheetSymbolHeight)
                        .width(bottomSheetSymbolWidth)
                        .clip(shape = RoundedCornerShape(bottomSheetSymbolRadius))
                        .background(color = inActiveInputBorderColor),
                )
            }
            bottomSheetTopRow()
            Spacer(modifier = Modifier.height(40.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row() {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(
                            text = "سال",
                            style = selectedTextStyle
                        )
                    }
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(
                            text = "ماه",
                            style = selectedTextStyle
                        )
                    }
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(
                            text = "روز",
                            style = selectedTextStyle
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(color = selectorColor, shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .height(48.dp)

                )
                Row() {

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    )
                    {
                        ListItemPicker(
                            label = { it.toString() },
                            value = selectedYear,
                            onValueChange = {

                                onDateChanged(it, selectedMonth, selectedDay)

                            },
                            list = yearSelectableRange, selectedTextStyle = selectedTextStyle,
                            unSelectedTextStyle = unSelectedTextStyle
                        )
                    }

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    )
                    {
                        ListItemPicker(
                            label = { it.toString() },
                            value = selectedMonth,
                            onValueChange = {
                                onDateChanged(selectedYear, it, selectedDay)
                            },
                            list = monthSelectableRange, selectedTextStyle = selectedTextStyle,
                            unSelectedTextStyle = unSelectedTextStyle
                        )
                    }
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    )
                    {
                        ListItemPicker(
                            label = { it.toString() },
                            value = selectedDay,
                            onValueChange = {
                                onDateChanged(selectedYear, selectedMonth, it)
                            },
                            list = daySelectableRange, selectedTextStyle = selectedTextStyle,
                            unSelectedTextStyle = unSelectedTextStyle
                        )
                    }
                }
            }

            if (showCurrentSelectedDate) {
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "$selectedDay ${monthNames[selectedMonth - 1]} $selectedYear",
                    style = currentSelectedDateTextStyle.copy(
                        textDirection = TextDirection.ContentOrRtl
                    )
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            if (customButton != null) {
                customButton(persianDate)
            } else {
                CustomButton(
                    text = buttonText ?: "",
                    onClick = { onButtonPressed(persianDate) },
                    enableContentStyle = buttonTextStyle ?: MaterialTheme.typography.body1,
                    verticalMargin = 0.0
                )
            }
        }
    }

    private fun onDateChanged(
        year: Int,
        month: Int,
        day: Int
    ) {
        persianDate.setDate(
            persianYear = year,
            persianMonth = month,
            persianDay = day
        )
        selectedYear = year
        selectedMonth = month
        selectedDay = day
        maxSelectableMonth = if (year == maxYear) {
            maxMonth
        } else {
            12
        }
        monthSelectableRange = (1..maxSelectableMonth).toList()
        maxSelectableDay = if (selectedYear == maxYear && month >= maxMonth) {
            maxDay

        } else if (persianDate.isLeapYear && month == 12) {
            30
        } else if (!persianDate.isLeapYear && month == 12) {
            29
        } else if (month <= 6) {
            31
        } else {
            30
        }
        daySelectableRange = (1..maxSelectableDay).toList()

    }
}
