package desidev.hango.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import desidev.hango.R
import desidev.hango.ui.screens.signup.account.AccountComponent.AccountCreateStatus
import desidev.hango.ui.screens.signup.account.AccountComponent.AccountCreateStatus.AccountCreateFailed
import desidev.hango.ui.screens.signup.account.AccountComponent.AccountCreateStatus.AccountCreated
import desidev.hango.ui.screens.signup.account.AccountComponent.AccountCreateStatus.CreatingAccount
import desidev.hango.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


@Preview
@Composable
private fun CreateAccountDialogPreview() {
    AppTheme {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            var state by rememberValueAsState<AccountCreateStatus>(value = CreatingAccount)

            LaunchedEffect(key1 = Unit) {
                while (isActive) {
                    state = CreatingAccount
                    delay(2000)
                    state = AccountCreated
                    delay(2000)
                    state =
                        AccountCreateFailed("Something wrong")
                    delay(2000)
                }
            }

            CreateAccountDialog(accountCreateStatus = state)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountDialog(
    accountCreateStatus: AccountCreateStatus
) {
    BasicAlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.80f)
                .animateContentSize(),
            shape = RoundedCornerShape(24.dp)
        ) {
            val modifier = Modifier.padding(16.dp)
            when (accountCreateStatus) {
                is CreatingAccount -> AccountCreatingStatus(modifier = modifier)
                is AccountCreated -> AccountCreateStatus(modifier = modifier)
                is AccountCreateFailed -> AccountCreateFailed(modifier = modifier)
            }
        }
    }
}


@Composable
fun AccountCreateStatus(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.account_complete),
            contentDescription = "account_complete",
            tint = Color.Unspecified
        )

        Text("Account Created", style = typography.titleMedium)
        Text(
            "Your account information has been added and you will be redirected to the home page in a few seconds.",
            style = typography.bodySmall,
            textAlign = TextAlign.Justify
        )
    }
}


@Composable
fun AccountCreatingStatus(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text("Creating Account", style = typography.titleMedium)
    }
}


@Composable
fun AccountCreateFailed(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.error),
            contentDescription = "account_failed",
            tint = colorScheme.error,
        )

        Text("Account Creation Failed", style = typography.titleMedium, color = colorScheme.error)
    }

}