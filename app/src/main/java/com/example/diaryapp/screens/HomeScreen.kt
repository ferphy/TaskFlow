package com.example.diaryapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.diaryapp.widgets.HomeTopAppBar

@Preview
@Composable
fun HomeScreen(){

    val taskNumber by remember { mutableIntStateOf(5) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = Color(0xFFFAFAFA)
        ){
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ){
                HomeTopAppBar()
                Task_to_do_title(taskNumber = taskNumber)
            }

        }
    }


}


@Composable
fun Task_to_do_title(
    taskNumber: Int,
    modifier: Modifier = Modifier
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