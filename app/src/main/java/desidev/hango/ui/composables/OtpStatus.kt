package desidev.hango.ui.composables

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
            otpState = AccountComponent.OTPState.SendingOtp
        )
    }
}

@Composable
fun OtpStatus(
    modifier: Modifier = Modifier,
    email: String,
    otpState: AccountComponent.OTPState
) {
    var statusText: String = ""

    when (otpState) {
        is AccountComponent.OTPState.SendingOtp -> {
            statusText = "Sending OTP to $email"
        }

        is AccountComponent.OTPState.OtpSent -> {
            statusText = "OTP sent to $email"
        }

        is AccountComponent.OTPState.OtpSentFailed -> {
            statusText = "Sending OTP failed"
        }

        is AccountComponent.OTPState.VerifyingOtp -> {
            statusText = "Verifying OTP"
        }

        is AccountComponent.OTPState.OtpVerificationFailed -> {
            statusText = "OTP verification failed"
        }

        else -> {
        /*ignore */
        }
    }

    Text(
        modifier = modifier,
        text = statusText
    )
}
