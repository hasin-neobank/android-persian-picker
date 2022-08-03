package com.example.picker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.picker.utils.bottomSheetSymbolHeight
import com.example.picker.utils.bottomSheetSymbolRadius
import com.example.picker.utils.bottomSheetSymbolWidth
import com.example.picker.utils.inActiveInputBorderColor

class AlphabetPicker(
    initialAlphabet: String
) {

    private var selectedAlphabet by mutableStateOf(initialAlphabet)

    @Composable
    fun AlphabetPickerUI(
        buttonTextStyle: TextStyle,
        selectedTextStyle: TextStyle,
        unSelectedTextStyle: TextStyle,
        buttonText: String,
        onButtonPressed: (alphabet: String) -> Unit,
        selectorColor: Color = Color(0xFFECEEF1),
    ) {

        val alphabets = listOf(
            "الف",
            "ب",
            "پ",
            "ت",
            "ث",
            "ج",
            "چ",
            "ح",
            "خ",
            "د",
            "ذ",
            "ر",
            "ز",
            "ژ",
            "س",
            "ش",
            "ص",
            "ض",
            "ط",
            "ظ",
            "ع",
            "غ",
            "ف",
            "ق",
            "ک",
            "گ",
            "ل",
            "م",
            "ن",
            "و",
            "ه",
            "ی"
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentHeight()
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
                Row() {


                    Box(
                        modifier = Modifier.weight(1f), contentAlignment = Alignment.Center
                    )
                    {
                        ListItemPicker(
                            label = { it },
                            value = selectedAlphabet,
                            onValueChange = {
                                selectedAlphabet = it
                            },
                            list = alphabets,
                            selectedTextStyle = selectedTextStyle,
                            unSelectedTextStyle = unSelectedTextStyle ,
                        )
                    }


                }

            }

            Spacer(modifier = Modifier.height(40.dp))
            CustomButton(
                text = buttonText,
                onClick = { onButtonPressed(selectedAlphabet) },
                enableContentStyle = buttonTextStyle,
                verticalMargin = 0.0
            )
        }

    }

}
