package com.example.datepicker.view


import android.widget.DatePicker
import androidx.compose.runtime.Composable
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


}