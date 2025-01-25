package com.example.diaryapp.widgets.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.ui.theme.dmSansFamily

@Preview
@Composable
fun TaskToDoTitle(
    taskNumber: Int = 7,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxWidth().height(32.dp)
    ){
        Text(
            modifier = modifier,
            fontSize = 24.sp,
            fontFamily = dmSansFamily,
            fontWeight = FontWeight.Bold,
            text = buildAnnotatedString {
                append("You’ve got ")
                withStyle(style = SpanStyle(color = Color(0xFFFFAE47))) { // Cambia el color aquí
                    append("$taskNumber")
                }
                append(" task today")
            },
            color = Color(0xFF074F60) // Este color será para el texto que no está en `taskNumber`
        )
    }

}
