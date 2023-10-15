package desidev.hango

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import desidev.hango.appstate.AppStore
import desidev.hango.ui.screens.SignUpScreen
import desidev.hango.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppStore.configStore()

        setContent {
            AppTheme(dynamicColor = false) {
                SignUpScreen(store = AppStore.store)
            }
        }
    }
}