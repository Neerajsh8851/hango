package desidev.hango.ui.screens.signin

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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import desidev.hango.ui.screens.signup.signup.PasswordVisibilityToggleIconButton
import desidev.hango.ui.theme.AppTheme


@Preview(name = "SignInScreen", showSystemUi = true, apiLevel = 33)
@Composable
fun SignInScreenPreview() {
    AppTheme(dynamicColor = false) {
        val bloc = remember { FakeSignInComponent() }
        SignInContent(bloc)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInContent(component: SignInComponent) {
    val emailAddr by component.userEmail.subscribeAsState()
    val password by component.userPassword.subscribeAsState()
    val hidePassword by component.hidePassword.subscribeAsState()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Sign In") })
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            ConstraintLayout {
                val ( inputsAndOps, signInOps) = createRefs()

                InputFieldsAndOptions(
                    emailAddr = emailAddr,
                    password = password,
                    hidePassword = hidePassword,
                    onEmailChange = { newValue -> component.updateEmail(newValue) },
                    onPasswordChange = { newValue -> component.updatePassword(newValue) },
                    onForgetPasswordClick = { component.forgetPasswordClick() },
                    passwordVisibilityToggle = { component.togglePasswordVisibility() },
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
                    onSignInClick = {
                        component.signInClick()
                    },
                    onSignUpClick = {
                        component.signUpClick()
                    }
                )
            }
        }
    }

}


@Composable
fun InputFieldsAndOptions(
    emailAddr: String,
    password: String,
    hidePassword: Boolean,
    passwordVisibilityToggle: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onForgetPasswordClick: () -> Unit,
    modifier: Modifier = Modifier,
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
        OutlinedTextField(
            modifier = Modifier.width(280.dp),
            label = { Text("Email Address") },
            value = emailAddr,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = keyboardActions
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            label = { Text(text = "Password") },
            value = password,
            onValueChange = {
                onPasswordChange(it)
            },
            singleLine = true,
            trailingIcon = {
                PasswordVisibilityToggleIconButton(passwordHidden = hidePassword, onToggle = {
                    passwordVisibilityToggle()
                })
            },
            visualTransformation = if (hidePassword) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .padding(bottom = 20.dp)
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