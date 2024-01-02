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
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
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
fun SignUpContent(component: SignUpComponent) {
    val userEmail by component.userEmail.subscribeAsState()
    val userPass by component.userPassword.subscribeAsState()
    val confirmPass by component.confirmPassword.subscribeAsState()
    val hidePassword by component.hidePassword.subscribeAsState()

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
                    component.onEvent(Event.UpdateEmail(it))
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
                        component.onEvent(Event.ToggleHidePassword)
                    })
                },
                onValueChange = {
                    component.onEvent(Event.UpdatePassword(it))
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
                    component.onEvent(Event.UpdateConfirmPassword(it))
                },
                modifier = Modifier.layoutId("confirmPass")
            )

            BottomContent(
                onSubmitClick = {},
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
    onSubmitClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Button(
            onClick = onSubmitClick,
            modifier = Modifier.width(280.dp)
        ) {
            Text(
                text = "Submit",
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
