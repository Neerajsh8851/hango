package desidev.hango.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import desidev.customnavigation.Component
import desidev.customnavigation.viewmodel.viewModelProvider
import desidev.hango.ui.components.EmailInputFieldComponent
import desidev.hango.ui.theme.AppTheme
import desidev.hango.viewmodels.SignUpViewModel
import desidev.hango.viewmodels.VerifyEmailState


@Preview
@Composable
fun Preview() {
    viewModelProvider.registerViewModelBuilderFor(SignUpViewModel::class)  { SignUpViewModel() }
    AppTheme {
        SignUpScreen.Content()
    }
}

object SignUpScreen : Component() {
    private val viewModel by lazy {
        getViewModel(SignUpViewModel::class)!!
    }

    @Composable
    override fun Content() {
        val state by viewModel.state.collectAsState()
        var emailAddressValue by remember { mutableStateOf("myemail@mydomain") }
        var otpValue by remember { mutableStateOf("") }

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
                    emailAddressValue = emailAddressValue,
                    onEmailAddressChange = { newValue ->
                        emailAddressValue = newValue
                    },
                    state = state,
                    otpValue = otpValue,
                    onOtpValueChange = { otpValue = it },
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
        emailAddressValue: String,
        otpValue: String,
        state: VerifyEmailState,
        onEmailAddressChange: (String) -> Unit,
        onOtpValueChange: (String) -> Unit
    ) {
        Column(modifier = modifier) {
            EmailInputFieldComponent(
                label = "Email Address",
                value = emailAddressValue,
                onValueChange = onEmailAddressChange
            )

            if (state is VerifyEmailState.EnterOtp) {
                EmailInputFieldComponent(
                    label = "Otp",
                    value = otpValue, onValueChange = onOtpValueChange
                )
            }
        }
    }

    @Composable
    fun BottomContent(
        modifier: Modifier,
        state: VerifyEmailState,
    ) {
        Column(
            modifier = modifier.padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            val btnText = when (state) {
                is VerifyEmailState.EnterEmail -> "Send Otp"
                is VerifyEmailState.EnterOtp -> "Verify"
                is VerifyEmailState.SetPassword -> "Sign-in"
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
}