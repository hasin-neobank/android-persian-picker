package com.example.picker

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.picker.utils.*
import com.example.picker.view.PersianBirthDatePicker
import com.example.picker.ui.theme.PersianDatePickerTheme
import com.example.picker.ui.theme.TextBlack
import com.example.picker.view.PersianExpirationDatePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val picker = PersianBirthDatePicker()
        setContent {
            PersianDatePickerTheme {
                val modalBottomSheetState =
                    rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
                val scope = rememberCoroutineScope()
                var result by remember {
                    mutableStateOf("Open Modal Bottom Sheet Layout")
                }
                ModalBottomSheetLayout(
                    sheetContent = {
                        Box(modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)) {
                        picker.BirthDatePickerUI(
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
                            onButtonPressed = { date ->
                                result= "current persian date: ${date.persianYear}/${date.persianMonth}/${date.persianDay}"
                            },
                        )
                        }
                    },
                    sheetState = modalBottomSheetState,
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    sheetBackgroundColor = Color.White,
                    // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
                ) {
                    Scaffold(
                        backgroundColor = Color.White
                    ) {
                        MainScreen(scope = scope, state = modalBottomSheetState, result)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(scope: CoroutineScope, state: ModalBottomSheetState, text: String) {
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
            Text(text = text)
        }
    }
}

