package desidev.hango.ui.screens.signup_process.account

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.value.getValue
import desidev.hango.ui.composables.CreateAccountDialog
import desidev.hango.ui.composables.OtpStatus
import desidev.hango.ui.screens.signup_process.account.AccountComponent.OtpStatus
import desidev.hango.ui.theme.AppTheme


@Preview
@Composable
fun AccountContentPreview() {
    AppTheme {
        AccountContent(component = remember { FakeAccountComponent() })
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun AccountContent(component: AccountComponent) {
    val tag = "AccountContent"
    val userEmail by component.userEmail
    val otpValue by component.otpValue.subscribeAsState()
    val uriHandler = LocalUriHandler.current

    val accountCreateStatus by component.accountCreateStatus.subscribeAsState()
    val otpStatus by component.otpStatus.subscribeAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        val headingTextId = "heading_text"
        val otpFieldId = "otp_field"
        val otpStatusId = "otp_status_text"
        val sendAgainBtnId = "send_again"
        val disclaimerTextId = "disclaimer"

        ConstraintLayout(
            modifier = Modifier.border(width = 1.dp, color = Color.Green),
            constraintSet = ConstraintSet {
                val headingText = createRefFor(headingTextId)
                val otpStatusText = createRefFor(otpStatusId)
                val otpField = createRefFor(otpFieldId)
                val sendAgainBtn = createRefFor(sendAgainBtnId)
                val disclaimer = createRefFor(disclaimerTextId)

                constrain(headingText) {
                    top.linkTo(parent.top, 20.dp)
                    centerHorizontallyTo(parent)
                }

                constrain(otpStatusText) {
                    top.linkTo(headingText.bottom, 20.dp)
                    bottom.linkTo(otpField.top)
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
            )


            OtpStatus(
                otpStatus = otpStatus,
                email = userEmail,
                modifier = Modifier
                    .layoutId(otpStatusId)
                    .fillMaxWidth(0.65f)
            )

            OutlinedTextField(
                value = otpValue,
                onValueChange = {
                    component.setOtp(it)
                },
                label = {
                    Text(text = "Enter OTP")
                },
                modifier = Modifier.layoutId(otpFieldId),
                textStyle = LocalTextStyle.current.copy(
                    letterSpacing = 0.4.em,
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            component.verifyOtp()
                        },
                        enabled = otpStatus is OtpStatus.OtpSent || otpStatus is OtpStatus.OtpInvalid
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                            contentDescription = "Check"
                        )
                    }
                },
                isError = otpStatus is OtpStatus.OtpInvalid,
                keyboardActions = KeyboardActions(onSend = {
                    component.verifyOtp()
                }),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Send
                )
            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.layoutId(sendAgainBtnId)
            ) {
                Text(text = "Did not get otp?")
                TextButton(onClick = { component.requestEmailAuth() }) {
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


    if (otpStatus is OtpStatus.OtpVerified) {
        CreateAccountDialog(accountCreateStatus = accountCreateStatus)
    }
}


