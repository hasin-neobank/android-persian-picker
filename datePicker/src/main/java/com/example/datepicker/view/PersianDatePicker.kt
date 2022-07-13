package com.example.datepicker.view

import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.datepicker.api.PersianPickerDate
import com.example.datepicker.utils.*

@Composable
fun PersianDatePicker(
    persianDate: PersianPickerDate? = null,
    selectedMonth: Int = 0,
    selectedYear: Int = 0,
    selectedDay: Int = 0,
    displayMonthNames: Boolean = false,
    mListener: DatePicker.OnDateChangedListener? = null,
    minYear: Int = 0,
    maxYear: Int = 0,
    maxMonth: Int = 0,
    maxDay: Int = 0,
    displayDescription: Boolean = false,
    yearRange: Int = 100,
    selectorColor: Color = Color(0xFFECEEF1),
    buttonText: String,
    buttonTextStyle: TextStyle? = null,
    selectedTextStyle: TextStyle? = null,
    unSelectedTextStyle: TextStyle? = null,
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