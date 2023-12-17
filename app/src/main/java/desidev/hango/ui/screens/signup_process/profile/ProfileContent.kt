package desidev.hango.ui.screens.signup_process.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import desidev.hango.ui.theme.AppTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text


@Preview
@Composable
fun ProfileContentPreview() {
    AppTheme {
        Surface {
            ProfileContent(
                bloc = remember { FakeProfileComponent() },
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
    }
}

@Composable
fun ProfileContent(bloc: ProfileComponent, modifier: Modifier = Modifier) {
    ProfileFields(modifier = modifier, bloc)
}


@Composable
private fun ProfileFields(modifier: Modifier = Modifier, bloc: ProfileComponent) {
    val name by bloc.name.collectAsState()

    Column(modifier = modifier) {
        RoundedTextField(
            value = name,
            onValueChange = { bloc.onEvent(Event.UpdateName(it)) },
            label = "Name",
            hint = "your name"
        )
    }
}

@Composable
fun RoundedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    hint: String = ""
) {
    Text(text = label, style = typography.labelLarge.copy(color = colorScheme.onSurface))
    Spacer(modifier = Modifier.height(12.dp))

    val contentTypo = typography.bodyMedium

    // user name field
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = contentTypo,
        decorationBox = { textContent ->
            Surface(shape = RoundedCornerShape(50.dp), color = colorScheme.surfaceContainer) {
                Box(
                    modifier = Modifier
                        .padding(15.dp)
                        .widthIn(min = 280.dp),
                    contentAlignment = Alignment.CenterStart,
                    propagateMinConstraints = true
                ) {
                    textContent()
                    if (value.isEmpty()) {
                        Text(text = hint, style = contentTypo.copy(color = colorScheme.outline))
                    }
                }
            }
        }
    )
}