package desidev.hango

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import desidev.hango.ui.screens.signup_process.DefaultSignUpProcess
import desidev.hango.ui.screens.signup_process.SignUpProcessContent
import desidev.hango.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = retainedComponent {
            DefaultSignUpProcess(it)
        }

        setContent {
            AppTheme {
                SignUpProcessContent(component = root)
            }
        }
    }
}




