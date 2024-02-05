package desidev.hango.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import desidev.hango.ui.theme.AppTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Preview(apiLevel = 33)
@Composable
fun DateInputPreview() {
    AppTheme {
        var date by rememberValueAsState(value = LocalDate.now())
        DateOfBirthInput(selectedDate = date, onDateSelected = { date = it })
    }
}


@Composable
fun DateOfBirthInput(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    label: String = "Birthdate",
    onDateSelected: (LocalDate) -> Unit,
) {
    var datePickerOpen by rememberValueAsState(value = false)
    val dateFormatter  = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    OutlinedTextField(
        modifier = modifier,
        label = { Text(text = label) },
        value = selectedDate.format(dateFormatter),
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { datePickerOpen = !datePickerOpen }) {
                Icon(imageVector = Icons.Rounded.DateRange, contentDescription = null)
            }
        }
    )

    if (datePickerOpen) {
        MyDatePickerDialog(
            onDateSelected = { date ->
                onDateSelected(date)
            },
            onDismiss = { datePickerOpen = false }
        )
    }
}


private fun convertMillisToDate(millis: Long): LocalDate {
    val instant = Instant.ofEpochMilli(millis)
    return LocalDate.ofInstant(instant, ZoneId.systemDefault())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    }

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    selectedDate?.let { onDateSelected(it) }
                    onDismiss()
                }
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}


