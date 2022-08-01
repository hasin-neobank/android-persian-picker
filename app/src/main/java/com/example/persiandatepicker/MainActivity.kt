package com.example.persiandatepicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.datepicker.view.AlphabetPicker
import com.example.datepicker.view.ListItemPicker
import com.example.datepicker.view.PersianDatePicker
import com.example.persiandatepicker.ui.theme.PersianDatePickerTheme
import com.example.persiandatepicker.ui.theme.TextBlack
import com.example.persiandatepicker.ui.theme.TextMediumGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val picker = PersianDatePicker(


            )
        picker.initValues()
        val alphabet = AlphabetPicker("ج")
        setContent {
            PersianDatePickerTheme {
                val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
                val scope = rememberCoroutineScope()
                 var result by remember {
                     mutableStateOf("Open Modal Bottom Sheet Layout")
                 }
                ModalBottomSheetLayout(
                    sheetContent = {
                        Box(modifier = Modifier.padding(top = 16.dp , bottom = 24.dp)){
                            alphabet.AlphabetPickerUI(
                            buttonTextStyle = MaterialTheme.typography.h3,
                            selectedTextStyle = MaterialTheme.typography.h3.copy(
                                color = TextBlack,
                                fontWeight = FontWeight.Normal
                            ),
                            unSelectedTextStyle = MaterialTheme.typography.h3.copy(
                                color = TextMediumGray,
                                fontWeight = FontWeight.Normal
                            ),
                            buttonText = "تایید",
                            onButtonPressed = { alphabet ->
                                result= "current alphabet is: $alphabet"
                            },
                        )}
                    },
                    sheetState = modalBottomSheetState,
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    sheetBackgroundColor = Color.White,
                    // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
                ) {
                    Scaffold(
                        backgroundColor = Color.White
                    ) {
                        MainScreen(scope = scope, state = modalBottomSheetState , result)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(scope: CoroutineScope, state: ModalBottomSheetState , text:String) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            ),
            onClick = {
                scope.launch {
                    state.show()
                }
            }) {
            Text(text =text )
        }
    }
}

