package desidev.hango.ui.screens.signup_process.signup

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import desidev.hango.R
import desidev.hango.ui.composables.EmailInputField
import desidev.hango.ui.composables.PasswordInputFieldComponent
import desidev.hango.ui.theme.AppTheme

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    AppTheme {
        val bloc = remember { FakeSignUpComponent() }
        SignUpContent(bloc)
    }
}

@Composable
fun SignUpContent(bloc: SignUpComponent) {
    val userEmail by bloc.userEmail.collectAsState()
    val userPass by bloc.userPassword.collectAsState()
    val confirmPass by bloc.confirmPassword.collectAsState()
    val hidePassword by bloc.hidePassword.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        val constraintSet = ConstraintSet {
            val heading = createRefFor("heading")
            val email = createRefFor("email")
            val password = createRefFor("password")
            val userConfPass = createRefFor("confirmPass")
            val bottomContent = createRefFor("bottomContent")

            constrain(bottomContent) {
                centerHorizontallyTo(parent)
                bottom.linkTo(parent.bottom)
            }

            val chain =
                createVerticalChain(email, password, userConfPass, chainStyle = ChainStyle.Packed)
            constrain(chain) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }

            constrain(heading) {
                top.linkTo(parent.top)
                bottom.linkTo(email.top)
                centerHorizontallyTo(parent)
            }

            constrain(email) { centerHorizontallyTo(parent) }
            constrain(password) { centerHorizontallyTo(parent) }
            constrain(userConfPass) { centerHorizontallyTo(parent) }
        }


        ConstraintLayout(constraintSet = constraintSet) {
            Text(
                text = "Create Hango Account",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.layoutId("heading")
            )

            EmailInputField(
                value = userEmail,
                label = "Email Address",
                onValueChange = {
                    bloc.onEvent(Event.UpdateEmail(it))
                },
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .layoutId("email")
            )

            PasswordInputFieldComponent(
                value = userPass,
                hidePassword = hidePassword,
                leadingIcon = {
                    PasswordVisibilityToggle(passwordHidden = hidePassword, onToggle = {
                        bloc.onEvent(Event.ToggleHidePassword)
                    })
                },
                onValueChange = {
                    bloc.onEvent(Event.UpdatePassword(it))
                },
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .layoutId("password")
            )


            PasswordInputFieldComponent(
                value = confirmPass,
                label = "Confirm Password",
                hidePassword = hidePassword,
                leadingIcon = { },
                onValueChange = {
                    bloc.onEvent(Event.UpdateConfirmPassword(it))
                },
                modifier = Modifier.layoutId("confirmPass")
            )

            BottomContent(
                onSignUpClick = {},
                modifier = Modifier.layoutId("bottomContent")
            )
        }
    }
}


@Composable
fun PasswordVisibilityToggle(
    modifier: Modifier = Modifier,
    passwordHidden: Boolean,
    onToggle: () -> Unit
) {
    val iconPainterResource =
        if (passwordHidden) {
            painterResource(id = R.drawable.visibility_24)
        } else {
            painterResource(id = R.drawable.visibility_off_24)
        }

    IconButton(onClick = onToggle, modifier = modifier) {
        Icon(
            painter = iconPainterResource,
            contentDescription = null
        )
    }
}



@Composable
private fun BottomContent(
    modifier: Modifier,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Button(
            onClick = onSignUpClick,
            modifier = Modifier.width(280.dp)
        ) {
            Text(
                text = "Sign up",
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
