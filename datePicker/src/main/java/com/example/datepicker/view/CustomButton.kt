package com.example.datepicker.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.datepicker.utils.DisableButtonColor
import com.example.datepicker.utils.EnableButtonColor
import com.example.datepicker.utils.TextMediumGray
import com.example.datepicker.utils.White

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    horizontalMargin: Double = 16.0,
    verticalMargin: Double = 16.0,
    enableBackgroundColor: Color = EnableButtonColor,
    disableBackgroundColor: Color = DisableButtonColor,
    enableBorderColor: Color = EnableButtonColor,
    disableBorderColor: Color = DisableButtonColor,
    borderWidth: Int = 1,
    enableContentStyle: TextStyle = MaterialTheme.typography.h3.copy(color = White),
    disableContentStyle: TextStyle = MaterialTheme.typography.h3.copy(color = TextMediumGray),
    isEnable: Boolean = true,
    onClick: () -> Unit,
    contentPadding: Double = 12.0,
    elevation: Double = 0.0,
    isFullWidth: Boolean = true,
    radius: Int = 12,
    isContentCenter: Boolean = true
) {
    Box(
        modifier = modifier.padding(
            horizontal = horizontalMargin.dp,
            vertical = verticalMargin.dp
        )
    ) {
        Button(
            onClick = onClick,
            modifier = if (isFullWidth) modifier.fillMaxWidth() else modifier
                .border(
                    width = borderWidth.dp,
                    color = if (isEnable) enableBorderColor else disableBorderColor,
                    shape = RoundedCornerShape(radius.dp)
                )
                .shadow(elevation = elevation.dp),
            enabled = isEnable,
            shape = RoundedCornerShape(radius.dp),
            border = BorderStroke(
                width = borderWidth.dp,
                color = if (isEnable) enableBorderColor else disableBorderColor,
            ),
            colors = ButtonDefaults.buttonColors(
                disabledBackgroundColor = disableBackgroundColor,
                backgroundColor = enableBackgroundColor
            ),
            contentPadding = PaddingValues(contentPadding.dp),
        ) {
            Text(
                text = text,
                style = if (isEnable) enableContentStyle else disableContentStyle,
                modifier = Modifier.fillMaxWidth(),
                textAlign = if (isContentCenter) TextAlign.Center else TextAlign.Start,
            )

        }
    }
}
