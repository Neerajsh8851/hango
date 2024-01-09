package desidev.hango.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composeuisuite.ohteepee.OhTeePeeInput
import com.composeuisuite.ohteepee.configuration.OhTeePeeCellConfiguration
import com.composeuisuite.ohteepee.configuration.OhTeePeeConfigurations
import desidev.hango.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Preview
@Composable
fun OtpTextFieldPreview() {
    AppTheme {
        val correctValue = "33333"
        var value by remember { mutableStateOf("") }
        var isValueInvalid by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        Surface {
            OtpInput(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
                otpValue = value,
                onValueChange = { newValue, isValid ->
                    value = newValue
                    if (isValid) {
                        isValueInvalid = correctValue != newValue
                        if (isValueInvalid) scope.launch {
                            delay(1000)
                            isValueInvalid = false
                            value = ""
                        }
                    }
                }, 
                isValueInvalid = isValueInvalid
            )
        }
    }
}

@Composable
fun OtpInput(
    modifier: Modifier = Modifier,
    otpValue: String,
    onValueChange: (String, Boolean) -> Unit,
    isValueInvalid: Boolean = false
) {
    // this config will be used for each cell
    val defaultCellConfig = OhTeePeeCellConfiguration.withDefaults(
        borderColor = colorScheme.outlineVariant,
        borderWidth = 1.dp,
        shape = RoundedCornerShape(8.dp),
        textStyle = typography.labelLarge.copy(color = colorScheme.onSurface)
    )

    OhTeePeeInput(
        modifier = modifier,
        value = otpValue,
        isValueInvalid = isValueInvalid,
        onValueChange = onValueChange,
        configurations = OhTeePeeConfigurations.withDefaults(
            cellsCount = 6,
            errorCellConfig = defaultCellConfig.copy(
                borderColor = Color.Red,
            ),
            emptyCellConfig = defaultCellConfig,
            activeCellConfig = defaultCellConfig.copy(
                borderColor = colorScheme.outline,
                borderWidth = 1.5.dp
            ),
            cellModifier = Modifier
                .padding(horizontal = 4.dp)
                .size(40.dp),
        )
    )
}