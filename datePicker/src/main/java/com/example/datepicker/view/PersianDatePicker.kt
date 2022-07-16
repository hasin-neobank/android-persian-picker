package com.example.datepicker.view

import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.datepicker.api.PersianPickerDate
import com.example.datepicker.date.PersianDateImpl
import com.example.datepicker.utils.bottomSheetSymbolHeight
import com.example.datepicker.utils.bottomSheetSymbolRadius
import com.example.datepicker.utils.bottomSheetSymbolWidth
import com.example.datepicker.utils.inActiveInputBorderColor
import java.util.*

@Composable
fun PersianDatePicker(
    preSelectedMonth: Int = 0,
    preSelectedYear: Int = 0,
    preSelectedDay: Int = 0,
    displayMonthNames: Boolean = false,
    yearRange: Int = 100,
    selectorColor: Color = Color(0xFFECEEF1),
    buttonText: String,
    onButtonPressed: (persianDate:PersianPickerDate )->Unit,
    buttonTextStyle: TextStyle? = null,
    selectedTextStyle: TextStyle? = null,
    unSelectedTextStyle: TextStyle? = null,
    minAge: Int = 18,
) {
    val dateInstance = Date()
    val cal: Calendar = Calendar.getInstance()
    cal.time = dateInstance
    cal.add(Calendar.YEAR, -(minAge))
    val dateBeforeMinAgeYears: Date = cal.time
    val persianDate: PersianPickerDate = PersianDateImpl()
    persianDate.setDate(dateBeforeMinAgeYears)
    val currentDay = persianDate.persianDay
    val currentMonth = persianDate.persianMonth
    var selectedYear by remember {
        mutableStateOf(preSelectedYear)
    }
    if (selectedYear == 0) {
        selectedYear = persianDate.persianYear
    }
    var selectedMonth by remember {
        mutableStateOf(preSelectedMonth)
    }
    if (selectedMonth == 0) {
        selectedMonth = currentMonth
    }
    var selectedDay by remember {
        mutableStateOf(preSelectedDay)
    }
    if (selectedDay == 0) {
        selectedDay = currentDay
    }
    val minYear = persianDate.persianYear - yearRange

    val maxYear = persianDate.persianYear
    val maxMonth = persianDate.persianMonth
    val maxDay = persianDate.persianDay

    //start UI code

    val yearRange = sparseListOf(1300..maxYear)
    var yearState by remember { mutableStateOf(yearRange[0]) }
    val monthRange = sparseListOf(1..12)
    var monthState by remember { mutableStateOf(monthRange[0]) }
    val dayRange = sparseListOf(1..31)
    var dayState by remember { mutableStateOf(dayRange[0]) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .height(bottomSheetSymbolHeight)
                .width(bottomSheetSymbolWidth)
                .clip(shape = RoundedCornerShape(bottomSheetSymbolRadius))
                .background(color = inActiveInputBorderColor),
        )
        Row() {

            ListItemPicker(
                label = { it.toString() },
                value = dayState,
                onValueChange = {
                    dayState = if(persianDate.isLeapYear&& monthState==12 && it>29) {
                        29
                    }else {
                        it
                    }
                    dayState = if(yearState==maxYear && monthState==maxMonth && it>maxDay) {
                        maxDay
                    }else {
                        it
                    }
                    dayState = if(monthState>=7 && it==31) {
                        30
                    }else {
                        it
                    }
                    persianDate.setDate(
                        persianYear = yearState,
                        persianMonth = monthState,
                        persianDay = dayState
                    )
                },
                list = dayRange
            )
            ListItemPicker(
                label = { persianDate.persianMonthName!! },
                value = monthState,
                onValueChange = {
                    monthState = if(yearState==maxYear&& monthState>maxMonth) {
                        maxMonth
                    }else {
                        it
                    }

                    persianDate.setDate(
                        persianYear = yearState,
                        persianMonth = monthState,
                        persianDay = dayState
                    )

                },
                list = monthRange
            )
            ListItemPicker(
                label = { it.toString() },
                value = yearState,
                onValueChange = {

                    yearState = if(yearState>maxYear) {
                        maxYear
                    }else {
                        it
                    }
                    persianDate.setDate(
                        persianYear = yearState,
                        persianMonth = monthState,
                        persianDay = dayState
                    )
                },
                list = yearRange
            )

        }
        CustomButton(text = buttonText, onClick = {onButtonPressed(persianDate)})
    }

}

fun sparseListOf(vararg ranges: IntRange): List<Int> = ranges.flatMap { it }