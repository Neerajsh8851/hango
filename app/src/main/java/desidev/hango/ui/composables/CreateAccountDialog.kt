package desidev.hango.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import desidev.hango.R
import desidev.hango.ui.screens.account.AccountComponent.AccountCreateStatus
import desidev.hango.ui.screens.account.AccountComponent.AccountCreateStatus.AccountComplete
import desidev.hango.ui.screens.account.AccountComponent.AccountCreateStatus.AccountCreateFailed
import desidev.hango.ui.screens.account.AccountComponent.AccountCreateStatus.CreatingAccount
import desidev.hango.ui.theme.AppTheme

@Preview
@Composable
private fun AccountCompleteDialogPreview() {
    AppTheme {
        Box(Modifier.fillMaxSize()) {
            CreateAccountDialog(accountCreateStatus = AccountComplete)
        }
    }
}

@Preview
@Composable
private fun AccountCreatingStatusDialogPreview() {
    AppTheme {
        Box(Modifier.fillMaxSize()) {
            CreateAccountDialog(accountCreateStatus = CreatingAccount)
        }
    }
}

@Preview
@Composable
private fun AccountCreateFailedDialogPreview() {
    AppTheme {
        Box(Modifier.fillMaxSize()) {
            CreateAccountDialog(accountCreateStatus = AccountCreateFailed("Something wrong"))
        }
    }
}


@Composable
private fun CreateAccountDialog(
    accountCreateStatus: AccountCreateStatus
) {
    when (accountCreateStatus) {
        is CreatingAccount -> AccountCreatingStatus()
        is AccountComplete -> AccountComplete()
        is AccountCreateFailed -> AccountCreateFailed(
            onRetry = {},
            onCancel = {}
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountComplete(modifier: Modifier = Modifier) {
    BasicAlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Surface(
            shape = shapes.large,
        ) {
            Column(
                modifier = modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.account_complete),
                    contentDescription = "account_complete",
                    tint = Color.Unspecified
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Account Complete", style = typography.headlineSmall)
                    Spacer(modifier = Modifier.padding(12.dp))
                    Text(
                        "Your account information has been added and you will be redirected to the home page in a few seconds.",
                        style = typography.bodyMedium,
                        textAlign = TextAlign.Justify
                    )
                }

                CircularProgressIndicator()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountCreatingStatus() {

    BasicAlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Surface(
            shape = shapes.medium,
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Creating Account", style = typography.headlineSmall)
                    Text(
                        text = "Please wait while we create your account.",
                        style = typography.bodyMedium
                    )
                }
            }
        }
    }

}


@Composable
fun AccountCreateFailed(
    onRetry: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.error),
                contentDescription = "account_failed",
                tint = colorScheme.error,
            )
        },
        title = {
            Text(
                "Account Creation Failed",
                style = typography.titleMedium,
                color = colorScheme.error,
            )
        },
        text = {
            Text(
                "Something went wrong while creating your account. Please try again later.",
                style = typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(onClick = { onRetry() }) {
                Text(
                    text = "Retry",
                    color = colorScheme.primary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text(
                    text = "Cancel",
                    color = colorScheme.primary
                )
            }
        }
    )
}