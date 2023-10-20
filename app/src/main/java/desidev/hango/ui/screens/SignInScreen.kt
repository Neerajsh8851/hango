package desidev.hango.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import desidev.hango.R
import desidev.hango.appstate.SignInScreenState
import desidev.hango.appstate.SignUpScreenState
import desidev.hango.appstate.navigation.NavigationAction
import desidev.hango.appstate.navigation.Screen
import desidev.hango.appstate.togglePasswordVisibility
import desidev.hango.appstate.updateEmail
import desidev.hango.appstate.updatePassword
import desidev.hango.states.StateValue
import desidev.hango.states.convert
import desidev.hango.ui.components.EmailInputFieldComponent
import desidev.hango.ui.components.PasswordInputFieldComponent
import desidev.hango.ui.theme.AppTheme


@Preview(name = "SignInScreen", showSystemUi = true)
@Composable
fun SignInScreenPreview() {
    AppTheme(dynamicColor = false) {
        SignInScreenContent(
            state = StateValue(
                SignInScreenState(
                    emailAddress = "",
                    password = "",
                    hidePassword = false
                )
            ),
            onNavigationAction = {
            }
        )
    }
}


@Composable
fun SignInScreenContent(
    state: StateValue<SignInScreenState>,
    onNavigationAction: (NavigationAction) -> Unit
) {
    val emailAddr by convert(state) { it.emailAddress }
    val password by convert(state) { it.password }
    val hidePassword by convert(state) { it.hidePassword }

    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout {
            val (topLabel, inputsAndOps, signInOps) = createRefs()

            Text(
                text = "Sign-in",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.constrainAs(topLabel) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top)
                    bottom.linkTo(inputsAndOps.top)
                })

            InputFieldsAndOptions(
                emailAddr = emailAddr,
                password = password,
                hidePassword = hidePassword,
                onEmailChange = { newValue -> state.updateEmail(newValue) },
                onPasswordChange = { newValue -> state.updatePassword(newValue) },
                onForgetPasswordClick = { },
                onPasswordVisiblityToggole = { state.togglePasswordVisibility() },
                modifier = Modifier.constrainAs(inputsAndOps) {
                    centerVerticallyTo(parent)
                    centerHorizontallyTo(parent)
                }
            )

            SignInOptions(
                modifier = Modifier.constrainAs(signInOps) {
                    bottom.linkTo(parent.bottom)
                    top.linkTo(inputsAndOps.bottom)
                    centerHorizontallyTo(parent)
                },
                onSignInClick = {},
                onSignUpClick = {
                    onNavigationAction(NavigationAction.Replace(
                        Screen.SignUpScreen(
                            StateValue(
                                SignUpScreenState()
                            )
                        )
                    ))
                }
            )
        }
    }
}


@Composable
fun InputFieldsAndOptions(
    emailAddr: String,
    password: String,
    hidePassword: Boolean,
    onPasswordVisiblityToggole: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onForgetPasswordClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val keyboardActions = remember {
        KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            },
            onDone = {
                focusManager.clearFocus()
            }
        )
    }

    Column(modifier = modifier.width(280.dp)) {
        EmailInputFieldComponent(
            modifier = Modifier.width(280.dp),
            label = "Email Address",
            value = emailAddr,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = keyboardActions
        )

        Spacer(modifier = Modifier.height(24.dp))

        PasswordInputFieldComponent(
            value = password,
            hidePassword = hidePassword,
            onValueChange = onPasswordChange,
            leadingIcon = {
                val iconPainterResource =
                    if (hidePassword) {
                        painterResource(id = R.drawable.visibility_24)
                    } else {
                        painterResource(id = R.drawable.visibility_off_24)
                    }

                IconButton(onClick = onPasswordVisiblityToggole) {
                    Icon(
                        painter = iconPainterResource,
                        contentDescription = null
                    )
                }
            },
            keyboardActions = keyboardActions
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Forgot password?",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onForgetPasswordClick
                )
                .padding(vertical = 8.dp)
                .align(Alignment.End)
        )
    }
}


@Composable
fun SignInOptions(
    modifier: Modifier,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Button(
            onClick = onSignInClick, modifier = Modifier
                .width(280.dp)
        ) {
            Text(text = "Sign-in")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(
                text = "New to Hango?",
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = "Sign-up",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.tertiary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable(
                        onClick = onSignUpClick,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    )
                    .padding(vertical = 8.dp)
            )
        }
    }
}