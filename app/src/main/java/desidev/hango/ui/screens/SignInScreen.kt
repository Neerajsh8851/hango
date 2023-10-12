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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import desidev.customnavigation.Screen
import desidev.hango.R
import desidev.hango.ui.components.EmailInputFieldComponent
import desidev.hango.ui.components.PasswordInputFieldComponent
import desidev.hango.ui.theme.AppTheme


@Preview(name = "SignInScreen", showSystemUi = true)
@Composable
fun SignInScreenPreview() {
    AppTheme(dynamicColor = false) {
        SignInScreen.Content()
    }
}

object SignInScreen : Screen() {
    @Composable
    override fun Content() {
        var emailAddr by remember { mutableStateOf("neezoytsharma@gmail.com") }
        var password by remember { mutableStateOf("Mm6grt8@") }

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
                    onEmailChange = { newValue -> emailAddr = newValue },
                    onPasswordChange = { newValue -> password = newValue },
                    onForgotPassword = { },
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
                    onSignUpClick = {}
                )
            }
        }
    }


    @Composable
    fun InputFieldsAndOptions(
        emailAddr: String,
        password: String,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onForgotPassword: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        var hidePassword by remember { mutableStateOf(true) }
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

                    IconButton(onClick = {
                        hidePassword = hidePassword.not()
                    }) {
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
                        onClick = onForgotPassword
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


}
