package com.example.datepicker.view

import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.datepicker.api.PersianPickerDate

@Composable
fun PersianDatePicker(
    persianDate: PersianPickerDate?,
    selectedMonth: Int = 0,
    selectedYear: Int = 0,
    selectedDay: Int = 0,
    displayMonthNames: Boolean = false,
    mListener: DatePicker.OnDateChangedListener? = null,
    yearNumberPicker: @Composable () -> Unit,
    monthNumberPicker: @Composable () -> Unit,
    dayNumberPicker: @Composable () -> Unit,
    minYear: Int = 0,
    maxYear: Int = 0,
    maxMonth: Int = 0,
    maxDay: Int = 0,
    displayDescription: Boolean = false,
    selectorColor: Color = Color(0xFFECEEF1),
    yearRange: Int = 100
) {
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
    Column() {
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

    }

}

fun sparseListOf(vararg ranges: IntRange): List<Int> = ranges.flatMap { it }