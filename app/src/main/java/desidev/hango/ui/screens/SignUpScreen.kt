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
import desidev.hango.appstate.AppStore.StateKeys
import desidev.hango.appstate.SignUpScreenState
import desidev.hango.states.Store
import desidev.hango.states.ValueDispatch
import desidev.hango.ui.collectAsState
import desidev.hango.ui.components.EmailInputFieldComponent
import desidev.hango.ui.components.PasswordInputFieldComponent
import desidev.hango.ui.theme.AppTheme

@Preview
@Composable
fun Preview() {
    AppTheme {
        val store = Store<StateKeys>().apply {
            config {
                put(StateKeys.VerifyEmailState) {
                    SignUpScreenState.EnterEmail(
                        email = "myemail@my.domain",
                        isValid = false
                    )
                }
            }
        }

        SignUpScreen(store = store)
    }
}

@Composable
fun SignUpScreen(store: Store<StateKeys>) {
    val stateRef = store.getRef<SignUpScreenState>(StateKeys.VerifyEmailState)
    val state by stateRef.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout {

            val (topLabel, textInputs, bottomContent) = createRefs()

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
                valueDispatch = stateRef,
                modifier = Modifier.constrainAs(textInputs) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                }
            )

            BottomContent(
                state = state,
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
    state: SignUpScreenState,
    valueDispatch: ValueDispatch<SignUpScreenState>,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        if (state is SignUpScreenState.EnterEmail) {
            EmailField(email = state.email, onEmailAddressChange = { newValue ->
                valueDispatch.dispatch(SignUpScreenState.UpdateEmail(newValue))
            })
        }

        if (state is SignUpScreenState.EnterOtp) {
            EmailField(
                email = state.email,
                onEmailAddressChange = {
                },
                readOnly = true
            )

            EmailInputFieldComponent(
                label = "One Time Pin",
                value = state.otp, onValueChange = {}
            )
        }

        if (state is SignUpScreenState.SetPassword) {
            EmailField(
                email = state.email,
                onEmailAddressChange = {},
                readOnly = true
            )

            PasswordInputFieldComponent(
                value = state.password,
                leadingIcon = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    }
                },
                onValueChange = { }
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
) {
    Column(
        modifier = modifier.padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val btnText = when (state) {
            is SignUpScreenState.EnterEmail -> "Send Otp"
            is SignUpScreenState.EnterOtp -> "Verify"
            is SignUpScreenState.SetPassword -> "Sign-in"
        }

        Button(
            onClick = { /*TODO*/ },
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