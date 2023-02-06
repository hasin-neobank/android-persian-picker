package com.example.picker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.picker.api.PersianPickerDate
import com.example.picker.date.PersianDateImpl
import com.example.picker.utils.bottomSheetSymbolHeight
import com.example.picker.utils.bottomSheetSymbolRadius
import com.example.picker.utils.bottomSheetSymbolWidth
import com.example.picker.utils.inActiveInputBorderColor

class PersianBirthDatePicker(
    preSelectedMonth: Int = 0,
    preSelectedYear: Int = 0,
    preSelectedDay: Int = 0,
    initialMaxMonth: Int? = null,
    initialMaxYear: Int? = null,
    initialMaxDay: Int? = null,
    yearRange: Int = 100,
    minAge: Int = 18,
) {
    private val persianDate: PersianPickerDate = PersianDateImpl()
    private var minYear = persianDate.persianYear - (minAge) - yearRange
    private val maxYear = initialMaxYear ?: (persianDate.persianYear - (minAge))
    private val maxMonth = initialMaxMonth ?: 12
    private val maxDay =
        initialMaxDay ?: if (persianDate.isLeapYear && persianDate.persianMonth == 12) {
            30
        } else if (!persianDate.isLeapYear && persianDate.persianMonth == 12) {
            29
        } else if (persianDate.persianMonth <= 6) {
            31
        } else {
            30
        }

    private val hasPreMaxValues =
        initialMaxYear != null || initialMaxMonth != null || initialMaxDay != null

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
            selectedMonth = initialMaxMonth?: persianDate.persianMonth
        }
        if (selectedDay == 0) {
            selectedDay =initialMaxDay?: persianDate.persianDay
        }
    }

    //start UI code
    @Composable
    fun BirthDatePickerUI(
        buttonTextStyle: TextStyle,
        selectedTextStyle: TextStyle,
        unSelectedTextStyle: TextStyle,
        buttonText: String,
        onButtonPressed: (persianDate: PersianPickerDate) -> Unit,
        selectorColor: Color = Color(0xFFECEEF1),
        buttonBackgroundColor: Color = Color(0xFF385473)
    ) {

        val monthNames = listOf(
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            // modifier = Modifier.wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .height(bottomSheetSymbolHeight)
                    .width(bottomSheetSymbolWidth)
                    .clip(shape = RoundedCornerShape(bottomSheetSymbolRadius))
                    .background(color = inActiveInputBorderColor),
            )
            Spacer(modifier = Modifier.height(40.dp))
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
                        modifier = Modifier.weight(1f), contentAlignment = Alignment.Center
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

                    Box(
                        modifier = Modifier.weight(1f), contentAlignment = Alignment.Center
                    )
                    {
                        ListItemPicker(
                            label = { monthNames[it - 1] },
                            value = selectedMonth,
                            onValueChange = {
                                onDateChanged(selectedYear, it, selectedDay)
                            },
                            list = monthSelectableRange, selectedTextStyle = selectedTextStyle,
                            unSelectedTextStyle = unSelectedTextStyle
                        )
                    }

                    Box(
                        modifier = Modifier.weight(1f), contentAlignment = Alignment.Center
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


                }

            }

            Spacer(modifier = Modifier.height(40.dp))
            CustomButton(
                text = buttonText,
                onClick = { onButtonPressed(persianDate) },
                enableContentStyle = buttonTextStyle,
                verticalMargin = 0.0,
                enableBorderColor = buttonBackgroundColor,
                enableBackgroundColor = buttonBackgroundColor
            )
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
        maxSelectableMonth = if (hasPreMaxValues && year == maxYear) {
            maxMonth
        } else {
            12
        }
        monthSelectableRange = (1..maxSelectableMonth).toList()
        maxSelectableDay = if (hasPreMaxValues && selectedYear == maxYear && month >= maxMonth) {
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
