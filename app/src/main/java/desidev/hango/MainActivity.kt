package desidev.hango

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import desidev.hango.appstate.AppState
import desidev.hango.appstate.navigation.NavigationModel
import desidev.hango.appstate.navigation.NavigationAction
import desidev.hango.appstate.navigation.Screen
import desidev.hango.states.StateValue
import desidev.hango.states.convert
import desidev.hango.ui.screens.SignInScreenContent
import desidev.hango.ui.theme.AppTheme
import java.time.LocalDateTime


var appState: AppState? = null

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appState = AppState(
            navigationState = StateValue(
                NavigationModel(
                    screen = Screen.LoadingScreen,
                    list = emptyList()
                )
            )
        )

        setContent {
            AppTheme {
                NavigationComp(navigationState = appState!!.navigationState, onExitApp = {
                    finishAfterTransition()
                })
            }
        }
    }
}


@Composable
fun NavigationComp(navigationState: StateValue<NavigationModel>, onExitApp: () -> Unit) {
    val currentScreen by convert(navigationState) { it.screen }

    Crossfade(currentScreen, label = "") { screen ->
        when (screen) {
            is Screen.LoadingScreen -> {
            }

            is Screen.SignInScreen -> {
                SignInScreenContent(
                    state = screen.signInScreenState,
                    onNavigationAction = {
                        navigationState.dispatch(it)
                    }
                )
            }

            is Screen.SignUpScreen -> {

            }

            is Screen.HomeScreen -> {}
        }
    }

    BackHandler {
        val screens = navigationState.getValue().list

        if (screens.isNotEmpty()) {
            navigationState.dispatch(
                NavigationAction.Back
            )
        } else {
            onExitApp()
        }
    }
}