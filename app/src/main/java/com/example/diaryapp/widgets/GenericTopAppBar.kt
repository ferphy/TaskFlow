package com.example.diaryapp.widgets

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.FactCheck
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.ui.theme.dmSansFamily

@Preview
@Composable
fun GenericTopAppBar(
    title: String = "New Task",
    modifier: Modifier = Modifier,
    leftIcon: ImageVector? = Icons.AutoMirrored.Filled.ArrowBack,
    rightIcon: ImageVector? = Icons.Default.Save,
    onLeftIconClick: () -> Unit = {},
    onRightIconClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFFFAFAFA))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Icon
            if (leftIcon != null) {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = "Left Icon",
                    tint = Color(0xFF074F60),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onLeftIconClick)
                )
            } else {
                Spacer(modifier = Modifier.size(24.dp)) // Placeholder para alinear el texto
            }

            // Title
            Text(
                text = title,
                fontFamily = dmSansFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF91919A)
            )

            // Right Icon
            if (rightIcon != null) {
                Icon(
                    imageVector = rightIcon,
                    contentDescription = "Right Icon",
                    tint = Color(0xFF074F60),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onRightIconClick)
                )
            } else {
                Spacer(modifier = Modifier.size(24.dp)) // Placeholder para alinear el texto
            }
        }
    }
}
