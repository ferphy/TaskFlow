package com.example.diaryapp.widgets.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TextFieldHint(
    modifier: Modifier = Modifier,
    hint: String = "hint",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onEnterPressed: (String) -> Unit,
    height: Dp = 60.dp
) {

    var text by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    val isFocused by interactionSource.collectIsFocusedAsState()
    val showHintAbove by remember {
        derivedStateOf {
            isFocused || text.isNotEmpty()
        }
    }

    BasicTextField(
        value = text,
        onValueChange = {newValue ->
            text = newValue
        },
        modifier = modifier,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        textStyle = textFieldTextStyle(),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        decorationBox = { innerTextField ->
            SharedTransitionLayout {
                AnimatedContent(
                    targetState = showHintAbove,
                    transitionSpec = {
                        EnterTransition.None togetherWith ExitTransition.None
                    },
                    label = "hintAnimation"
                ) { showHintAbove ->
                    Column {
                        Box(Modifier.padding(start = 2.dp)) {
                            InvisibleTextAsPlaceholder(exteriorHintTextStyle())
                            if (showHintAbove) {
                                TextAsIndividualLetters(
                                    animatedContentScope = this@AnimatedContent,
                                    text = hint,
                                    style = exteriorHintTextStyle(),
                                )
                            }
                        }
                        Spacer(Modifier.height(2.dp))
                        Box(
                            modifier = Modifier
                                .sharedElement(
                                    rememberSharedContentState(key = "input"),
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                                .fillMaxWidth()
                                .height(height)
                                .background(
                                    color = Color(0xFFE8EDF2),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .border(
                                    width = Dp.Hairline,
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = .3f
                                    )
                                )
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                        ) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                if (!showHintAbove) {
                                    TextAsIndividualLetters(
                                        animatedContentScope = this@AnimatedContent,
                                        text = hint,
                                        style = interiorHintTextStyle(),
                                        modifier = Modifier.align(Alignment.Start)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .align(Alignment.Start)
                                ) {
                                    innerTextField()
                                }
                            }
                        }
                    }
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done // Configurar la acción del teclado como "Done"
        ),
        keyboardActions = KeyboardActions(
            onDone = { onEnterPressed(text) } // Manejar el evento "Done" (Enter)
        ),
    )
}

@Composable
fun InvisibleTextAsPlaceholder(style: TextStyle) {
    Text(
        text = "",
        modifier = Modifier.alpha(0f),
        style = style,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.TextAsIndividualLetters(
    animatedContentScope: AnimatedContentScope,
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(),
) {
    Row(modifier) {
        text.forEachIndexed { index, letter ->
            Text(
                text = "$letter",
                modifier = Modifier.sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "hint_$index"),
                    animatedVisibilityScope = animatedContentScope,
                    boundsTransform = { _, _ ->
                        spring(
                            stiffness = 25f * (text.length - index),
                            dampingRatio = Spring.DampingRatioLowBouncy,
                        )
                    }
                ),
                style = style,
            )
        }
    }
}

@ReadOnlyComposable
@Composable
fun textFieldTextStyle() = MaterialTheme.typography.labelLarge.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .9f),
)

@ReadOnlyComposable
@Composable
fun exteriorHintTextStyle() = MaterialTheme.typography.labelLarge.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
    color = Color(0xFF055062),
)

@ReadOnlyComposable
@Composable
fun interiorHintTextStyle() = textFieldTextStyle().copy(
    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .4f),
    fontSize = 20.sp,
)