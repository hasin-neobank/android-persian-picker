package com.example.picker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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

class PersianExpirationDatePicker(
    preSelectedMonth: Int = 0,
    preSelectedYear: Int = 0,
    yearRange: Int = 100,
) {
    private val persianDate: PersianPickerDate = PersianDateImpl()
    private var minYear = persianDate.persianYear
    private val maxYear = persianDate.persianYear + yearRange
    private val minMonth = persianDate.persianMonth
    private var selectedYear by mutableStateOf(preSelectedYear)
    private var selectedMonth by mutableStateOf(preSelectedMonth)
    private var selectedDay by mutableStateOf(if (preSelectedMonth <= 6) 31 else 30)
    private var maxSelectableYear by mutableStateOf(maxYear)
    private var yearSelectableRange by
    mutableStateOf((minYear..maxSelectableYear).toList())

    private var minSelectableMonth by mutableStateOf(minMonth)
    private var monthSelectableRange
            by mutableStateOf((minSelectableMonth..12).toList())

    init {
        if (selectedYear == 0) {
            selectedYear = minYear
        }
        if (selectedMonth == 0) {
            selectedMonth = minMonth
        }
        persianDate.setDate(
            selectedYear,
            selectedMonth,
            selectedDay
        )
    }

    //start UI code
    @Composable
    fun ExpirationDatePickerUI(
        buttonTextStyle: TextStyle,
        selectedTextStyle: TextStyle,
        unSelectedTextStyle: TextStyle,
        buttonText: String,
        onButtonPressed: (persianDate: PersianPickerDate) -> Unit,
        selectorColor: Color = Color(0xFFECEEF1),
        buttonBackgroundColor: Color = Color(0xFF385473)
    ) {

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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterEnd,
                    )
                    {
                        ListItemPicker(
                            label = { if (it < 10) "0${it}" else it.toString() },
                            value = selectedMonth,
                            onValueChange = {
                                onDateChanged(selectedYear, it)
                            },
                            list = monthSelectableRange, selectedTextStyle = selectedTextStyle,
                            unSelectedTextStyle = unSelectedTextStyle
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text("/", style = selectedTextStyle)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart,
                    )
                    {
                        ListItemPicker(
                            label = { it.toString() },
                            value = selectedYear,
                            onValueChange = {

                                onDateChanged(it, selectedMonth)

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
        month: Int
    ) {
        persianDate.setDate(
            persianYear = year,
            persianMonth = month,
            persianDay = if (month <= 6) 31 else 30
        )
        selectedYear = year
        selectedMonth = month
        selectedDay = if (month <= 6) 31 else 30
        minSelectableMonth = if (year == minYear) {
            minMonth
        } else {
            1
        }
        monthSelectableRange = (minSelectableMonth..12).toList()
    }
}
