package com.example.datepicker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.datepicker.api.PersianPickerDate
import com.example.datepicker.date.PersianDateImpl
import com.example.datepicker.utils.bottomSheetSymbolHeight
import com.example.datepicker.utils.bottomSheetSymbolRadius
import com.example.datepicker.utils.bottomSheetSymbolWidth
import com.example.datepicker.utils.inActiveInputBorderColor

class PersianDatePicker(
    preSelectedMonth: Int = 0,
    preSelectedYear: Int = 0,
    preSelectedDay: Int = 0,
    yearRange: Int = 100,
    private val minAge: Int = 18,
){
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

    fun initValues() {
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
        buttonTextStyle: TextStyle,
        selectedTextStyle: TextStyle,
        unSelectedTextStyle: TextStyle,
         buttonText: String,
         onButtonPressed: (persianDate: PersianPickerDate) -> Unit,
        selectorColor: Color = Color(0xFFECEEF1),
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
                Spacer(modifier = Modifier.height(90.dp))
                Box(contentAlignment = Alignment.Center , modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Box(
                        modifier = Modifier
                            .background(color = selectorColor,shape = RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .height(50.dp)

                    )
                    Row() {


                        ListItemPicker(
                            label = { it.toString() },
                            value = selectedYear,
                            onValueChange = {

                                onDateChanged(it, selectedMonth, selectedDay)

                            },
                            list = yearSelectableRange, selectedTextStyle = selectedTextStyle,
                            unSelectedTextStyle = unSelectedTextStyle
                        )

                        Box(modifier = Modifier.weight(1f)
                            , contentAlignment = Alignment.Center)
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

                Spacer(modifier = Modifier.height(90.dp))
                CustomButton(
                    text = buttonText,
                    onClick = { onButtonPressed(persianDate) },
                    enableContentStyle = buttonTextStyle ,
                    verticalMargin = 0.0
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
