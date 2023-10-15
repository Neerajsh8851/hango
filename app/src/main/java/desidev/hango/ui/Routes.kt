package desidev.hango.ui

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import desidev.hango.states.Reference
import desidev.hango.states.StateValue
import desidev.hango.states.Store


val routeStore: Store<String> = Store()

sealed class Route(
    val content: @Composable () -> Unit
) {
    data object SignInScreen : Route(content = {})
    data object SignUpScreen : Route(content = {})

    companion object {
        fun config() {
            routeStore.config {
                put("route") { StateValue(SignInScreen) }
            }
        }
    }
}


@Composable
fun <T> Reference<T>.collectAsState(): State<T> {

    val state = remember { mutableStateOf(getValue()) }

    DisposableEffect(key1 = Unit, effect = {
        observe { o, n ->
            state.value = n
        }
        onDispose {
            dispose()
        }
    })

    return state
}



@Composable
fun Test() {
    Route.config()
}


@Composable
fun ApplicationRoutes() {
    Route.config()

    val route by routeStore.getRef<Route>("route").collectAsState()

    Crossfade(targetState = route, label = "routing") { currentRoute ->
        when (currentRoute) {
            is Route.SignInScreen -> {

            }

            is Route.SignUpScreen -> {

            }
        }
    }
}



