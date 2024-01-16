package desidev.hango.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun <T> rememberValueState(value: T) = remember { mutableStateOf(value) }