package desidev.hango.ui.screens.signup_process.auth

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import desidev.hango.ui.theme.AppTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.value.getValue
import desidev.hango.ui.composables.OtpInput


@Preview
@Composable
fun AuthContentPreview() {
    AppTheme {
        AuthContent(component = remember { FakeAuthComponent() })
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun AuthContent(component: AuthComponent) {
    val userEmail by component.userEmail
    val otpValue by component.optValue.collectAsState()
    val uriHandler = LocalUriHandler.current

    Surface(modifier = Modifier.fillMaxSize()) {
        val headingTextId = "heading_text"
        val emailTextId = "email_text"
        val otpFieldId = "otp_field"
        val otpStatusTextId = "otp_status_text"
        val sendAgainBtnId = "send_again"
        val disclaimerTextId = "disclaimer"

        ConstraintLayout(
            modifier = Modifier.border(width = 1.dp, color = Color.Green),
            constraintSet = ConstraintSet {
                val headingText = createRefFor(headingTextId)
                val emailText = createRefFor(emailTextId)
                val otpStatusText = createRefFor(otpStatusTextId)
                val otpField = createRefFor(otpFieldId)
                val sendAgainBtn = createRefFor(sendAgainBtnId)
                val disclaimer = createRefFor(disclaimerTextId)

                val chainRef = createVerticalChain(
                    headingText,
                    otpStatusText,
                    emailText,
                    chainStyle = ChainStyle.Packed
                )

                constrain(chainRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(otpField.top)
                }

                constrain(emailText) {
                    centerHorizontallyTo(parent)
                }

                constrain(headingText) {
                    centerHorizontallyTo(parent)
                }

                constrain(otpStatusText) {
                    centerHorizontallyTo(parent)
                }

                constrain(otpField) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                }

                constrain(sendAgainBtn) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(disclaimer.top, 20.dp)
                }

                constrain(disclaimer) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom, 20.dp)
                }
            }
        ) {
            Text(
                text = "Verify your Email",
                style = typography.headlineMedium.copy(colorScheme.onSurface),
                modifier = Modifier
                    .layoutId(headingTextId)
                    .padding(bottom = 20.dp)
            )

            Text(
                text = "An OTP has been sent to your email ",
                style = typography.bodySmall.copy(colorScheme.onSurface),
                modifier = Modifier.layoutId(otpStatusTextId)
            )

            Text(
                text = userEmail,
                style = typography.labelLarge.copy(color = colorScheme.onSurface),
                modifier = Modifier.layoutId(emailTextId)
            )

            OtpInput(
                otpValue = otpValue,
                onValueChange = { newValue, isValid ->
                    component.updateOtp(newValue)
                    if (isValid) {
                        Log.d("AuthContent", "Entered a valid otp")
                    }
                },
                modifier = Modifier.layoutId(otpFieldId)
            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.layoutId(sendAgainBtnId)
            ) {
                Text(text = "Did not get otp?")
                TextButton(onClick = { component.requestNewOtp() }) {
                    Text(text = "Send again")
                }
            }

            val linkColor = colorScheme.primary
            val annotatedText = buildAnnotatedString {
                append("By signing up to Hango, you are accepting\n our")

                // TODO: Replace annotated string link to privacy and policy.
                withAnnotation(tag = "url", annotation = "https://www.google.com") {
                    withStyle(style = SpanStyle(color = linkColor)) {
                        append(" Terms & Conditions of use.")
                    }
                }
            }

            ClickableText(
                text = annotatedText,
                onClick = {
                    annotatedText.getStringAnnotations("url", it, it)
                        .firstOrNull()?.let { annotatedString ->
                            uriHandler.openUri(annotatedString.item)
                        }
                },
                style = typography.bodyMedium.copy(textAlign = TextAlign.Center),
                modifier = Modifier.layoutId(disclaimerTextId),
            )
        }
    }
}