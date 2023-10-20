package desidev.hango.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import desidev.hango.appstate.navigation.NavigationAction
import desidev.hango.appstate.SignUpScreenState
import desidev.hango.appstate.SignUpScreenStateAction
import desidev.hango.appstate.UiState
import desidev.hango.states.StateValue
import desidev.hango.states.convert
import desidev.hango.ui.components.EmailInputFieldComponent
import desidev.hango.ui.components.PasswordInputFieldComponent
import desidev.hango.ui.theme.AppTheme

@Preview
@Composable
fun Preview() {
    AppTheme {
        SignUpScreen(
            state = StateValue(SignUpScreenState()),
            navigationAction = { navigationAction -> }
        )
    }
}

@Composable
fun SignUpScreen(
    state: StateValue<SignUpScreenState>,
    navigationAction: (NavigationAction) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout {
            val (topLabel, textInputs, bottomContent) = createRefs()

            val signUpScreenState by convert(state) { it }
            val buttonAction = when (signUpScreenState.uiState) {
                UiState.EditEmail -> SignUpScreenStateAction.SendOtp
                UiState.SignUp -> TODO()
                UiState.EnterOtp -> TODO()
            }

            Text(
                text = "Verify Your Email",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.constrainAs(topLabel) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top)
                    bottom.linkTo(textInputs.top)
                }
            )

            TextInput(
                state = state,
                modifier = Modifier.constrainAs(textInputs) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                }
            )

            BottomContent(
                state = signUpScreenState,
                onButtonPressed = { state.dispatch(buttonAction) },
                modifier = Modifier.constrainAs(bottomContent) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
    }
}

@Composable
fun TextInput(
    modifier: Modifier,
    state: StateValue<SignUpScreenState>,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        val email by convert(state) { it.email }
        val password by convert(state) { it.password }
        val otpSent by convert(state) { it.isOtpSent }

        EmailField(
            email = email,
            readOnly = otpSent,
            onEmailAddressChange = { newValue ->
                state.dispatch(
                    SignUpScreenStateAction.UpdateEmail(newValue)
                )
            })

        if (otpSent) {
            PasswordInputFieldComponent(
                value = password,
                leadingIcon = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    }
                },
                onValueChange = {
                }
            )
        }

    }
}


@Composable
fun EmailField(email: String, readOnly: Boolean = false, onEmailAddressChange: (String) -> Unit) {
    EmailInputFieldComponent(
        label = "Email Address",
        value = email,
        readOnly = readOnly,
        onValueChange = onEmailAddressChange,
    )
}

@Composable
fun BottomContent(
    modifier: Modifier,
    state: SignUpScreenState,
    onButtonPressed: () -> Unit
) {
    Column(
        modifier = modifier.padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val btnText = when (state.uiState) {
            UiState.EditEmail -> "Send Otp"
            UiState.EnterOtp -> "Verify"
            UiState.SignUp -> "Sign Up"
        }

        Button(
            onClick = onButtonPressed,
            modifier = Modifier.width(280.dp)
        ) {
            Text(
                text = btnText,
                modifier = Modifier.animateContentSize()
            )
        }

        Text(
            text = "By signing up to Hango, you are accepting\n" +
                    "our Terms & Conditions of use ",
            textAlign = TextAlign.Center
        )
    }
}