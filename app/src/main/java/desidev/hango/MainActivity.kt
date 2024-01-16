package desidev.hango

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import desidev.hango.api.DefaultAuthService
import desidev.hango.ui.screens.signup_process.DefaultSignUpComponent
import desidev.hango.ui.screens.signup_process.SignUpContent
import desidev.hango.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = retainedComponent { componentContext ->
            DefaultSignUpComponent(
                context = componentContext,
                authService = DefaultAuthService("http://139.59.85.69"),
                onAccountCreated = {
                    Log.d(TAG, "on account created : $it")
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




