package desidev.hango

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import desidev.customnavigation.Content
import desidev.customnavigation.ParentComponent
import desidev.hango.ui.screens.SignInScreen
import desidev.hango.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val rootContent: Content = remember {
                        object : ParentComponent(SignInScreen) {
                            override fun onFinish() {
                                this@MainActivity.finishAfterTransition()
                            }
                        }
                    }
                    rootContent.Content()
                }
            }
        }
    }
}