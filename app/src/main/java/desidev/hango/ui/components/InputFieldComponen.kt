package desidev.hango.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import desidev.hango.ui.theme.AppTheme

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            EmailInputFieldComponent(
                value = "Some",
                label = "Email Address",
                onValueChange = { newValue -> },
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Composable
fun EmailInputFieldComponent(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    readOnly: Boolean = false,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    onValueChange: (newValue: String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val textStyle = MaterialTheme.typography.labelLarge.copy(
        color = MaterialTheme.colorScheme.onSurface
    )
    val decorationBox: @Composable (@Composable () -> Unit) -> Unit = { innerTextComponent ->
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .heightIn(min = 48.dp)
                .widthIn(min = 280.dp)
                .background(
                    color = containerColor,
                    shape = RoundedCornerShape(50.dp)
                )
                .padding(horizontal = 16.dp)
        ) {
            innerTextComponent()
        }
    }

    Column(modifier = modifier) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(12.dp))
        BasicTextField(
            value = value,
            readOnly = readOnly,
            onValueChange = onValueChange,
            decorationBox = decorationBox,
            textStyle = textStyle,
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
    }
}


@Preview
@Composable
fun PasswordInputFieldPreview() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            var password by remember { mutableStateOf("test-password") }
            val localFocusManager = LocalFocusManager.current

            PasswordInputFieldComponent(
                modifier = Modifier.padding(16.dp),
                value = password,
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    }
                },
                onValueChange = { newValue -> password = newValue },
                keyboardActions = KeyboardActions {
                    localFocusManager.clearFocus()
                }
            )
        }
    }
}


@Composable
fun PasswordInputFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    label: String = "Password",
    hidePassword: Boolean = true,
    leadingIcon: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (newValue: String) -> Unit,
) {

    val textStyle = MaterialTheme.typography.labelLarge.copy(
        color = MaterialTheme.colorScheme.onSurface
    )

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.height(12.dp))
        BasicTextField(
            modifier = Modifier.onFocusChanged { focusState ->
                println("FocusState: ${focusState.isFocused}")
            },
            value = value,
            onValueChange = onValueChange,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .heightIn(min = 48.dp)
                        .widthIn(min = 280.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    innerTextField()
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterEnd)
                    ) {
                        leadingIcon()
                    }
                }
            },
            textStyle = textStyle,
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = if (hidePassword) PasswordVisualTransformation() else VisualTransformation.None
        )
    }

}