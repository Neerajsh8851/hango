package desidev.hango

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import desidev.hango.api.DefaultAuthService
import desidev.hango.ui.screens.signup_process.DefaultSignUpComponent
import desidev.hango.ui.screens.signup_process.SignUpContent
import desidev.hango.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = retainedComponent {
            DefaultSignUpComponent(
                context = it,
                authService = DefaultAuthService("http://139.59.85.69"),
                onAccountCreated = {

                }
            )
        }

        setContent {
            AppTheme {
                SignUpContent(component = root)
            }
        }
    }
}




