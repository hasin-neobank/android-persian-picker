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
    mListener: DatePicker.OnDateChangedListener? = null,
    maxYear: Int = 0,
    maxMonth: Int = 0,
    maxDay: Int = 0,
    yearRange: Int = 100,
    selectorColor: Color = Color(0xFFECEEF1),
    buttonText: String,
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
    if(selectedYear==0){
        selectedYear = persianDate.persianYear
    }
    var selectedMonth by remember {
        mutableStateOf(preSelectedMonth)
    }
    if(selectedMonth==0){
        selectedMonth = currentMonth
    }
    var selectedDay by remember {
        mutableStateOf(preSelectedDay)
    }
    if(selectedDay==0){
        selectedDay = currentDay
    }
    val minYear = persianDate.persianYear-yearRange


    //start UI code
    val dayRange = sparseListOf(1..31)
    var dayState by remember { mutableStateOf(dayRange[0]) }
    val yearRange = sparseListOf(1300..1400)
    var yearState by remember { mutableStateOf(yearRange[0]) }
    val monthRange = listOf(
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
    var monthState by remember { mutableStateOf(monthRange[0]) }
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
                onValueChange = { dayState = it },
                list = dayRange
            )
            ListItemPicker(
                label = { it.toString() },
                value = monthState,
                onValueChange = { monthState = it },
                list = monthRange
            )
            ListItemPicker(
                label = { it.toString() },
                value = yearState,
                onValueChange = { yearState = it },
                list = yearRange
            )

        }
        CustomButton(text = buttonText, onClick = {})
    }

}

fun sparseListOf(vararg ranges: IntRange): List<Int> = ranges.flatMap { it }