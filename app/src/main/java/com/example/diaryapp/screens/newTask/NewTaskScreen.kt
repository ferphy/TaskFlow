package com.example.diaryapp.screens.newTask

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.diaryapp.navigation.NewTaskScreenRoute
import com.example.diaryapp.widgets.FloatingActionButtonHome
import com.example.diaryapp.widgets.HomeTopAppBar
import com.example.diaryapp.widgets.TextFieldHint

@Composable
fun NewTaskScreen() {

    val firstField = remember { mutableStateOf("") }
    val secondField = remember { mutableStateOf("") }
    val thirdField = remember { mutableStateOf("") }

    // FocusRequesters para manejar el enfoque entre campos
    val firstFocusRequester = remember { FocusRequester() }
    val secondFocusRequester = remember { FocusRequester() }
    val thirdFocusRequester = remember { FocusRequester() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(), color = Color(0xFFFAFAFA)
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                HomeTopAppBar(modifier = Modifier.padding(horizontal = 10.dp))

                TextFieldHint(
                    hint = "Tittle",
                    onEnterPressed = {
                        firstField.value = it
                        secondFocusRequester.requestFocus()
                    },
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .focusRequester(firstFocusRequester)
                )

            }
        }
    }
}