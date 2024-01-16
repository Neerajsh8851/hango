package desidev.hango.ui.composables

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import desidev.hango.ui.screens.signup_process.account.AccountComponent
import desidev.hango.ui.theme.AppTheme


@Preview
@Composable
private fun OtpStatusPreview() {
    AppTheme {
        OtpStatus(
            email = "someuser@desidev.online",
            otpStatus = AccountComponent.OtpStatus.SendingOtp
        )
    }
}

@Composable
fun OtpStatus(
    modifier: Modifier = Modifier,
    email: String,
    otpStatus: AccountComponent.OtpStatus
) {
    when (otpStatus) {
        is AccountComponent.OtpStatus.SendingOtp, AccountComponent.OtpStatus.BeforeSendingOtp -> {
            Text(text = "Sending Otp to your email \n $email", modifier = modifier)
        }

        is AccountComponent.OtpStatus.OtpSent -> {
            Text(
                text = "Otp sent to your email\n $email",
                modifier = modifier
            )
        }

        is AccountComponent.OtpStatus.OtpVerified -> {
            Text(text = "Otp verified!", modifier = modifier)
        }

        is AccountComponent.OtpStatus.OtpVerificationError -> {
            Text(
                text = "Otp verification failed!",
                color = colorScheme.error,
                modifier = modifier
            )
        }

        is AccountComponent.OtpStatus.NoAttemptsLeft -> {
            Text(
                text = "No attempts left!",
                color = colorScheme.error,
                modifier = modifier
            )
        }

        is AccountComponent.OtpStatus.OtpExpired -> {
            Text(
                text = "Otp expired!",
                color = colorScheme.error,
                modifier = modifier
            )
        }

        is AccountComponent.OtpStatus.OtpInvalid -> {
            Text(
                text = "Otp invalid!",
                color = colorScheme.error,
                modifier = modifier
            )
        }

        is AccountComponent.OtpStatus.OtpSentFailed -> {
            Text(
                text = "Otp sending failed!",
                color = colorScheme.error,
                modifier = modifier
            )
        }

        is AccountComponent.OtpStatus.VerifyingOtp -> {
            Text(
                text = "Verifying otp...",
                modifier = modifier
            )
        }
    }
}
