package com.example.persiandatepicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.datepicker.view.ListItemPicker
import com.example.datepicker.view.PersianDatePicker
import com.example.persiandatepicker.ui.theme.PersianDatePickerTheme
import com.example.persiandatepicker.ui.theme.TextBlack
import com.example.persiandatepicker.ui.theme.TextMediumGray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val picker = PersianDatePicker(
            buttonText = "تایید",
            onButtonPressed = { date -> println("current persian date: ${date.persianYear}/${date.persianMonth}/${date.persianDay}") },

            )
        picker.initValues()
        setContent {
            PersianDatePickerTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
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
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PersianDatePickerTheme {
        Greeting("Android")
    }
}

