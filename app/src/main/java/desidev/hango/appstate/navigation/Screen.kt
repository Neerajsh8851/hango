package desidev.hango.appstate.navigation

import desidev.hango.appstate.HomeScreenModel
import desidev.hango.appstate.SignInScreenState
import desidev.hango.appstate.SignUpScreenState
import desidev.hango.states.StateValue

sealed class Screen {
    data object LoadingScreen : Screen()

    data class SignInScreen(
        val signInScreenState: StateValue<SignInScreenState>
    ) : Screen()

    data class SignUpScreen(
        val signUpScreenState: StateValue<SignUpScreenState>
    ) : Screen()

    data class HomeScreen(val state: HomeScreenModel): Screen()
}
