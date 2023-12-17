package desidev.hango.ui.screens.signup_process

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.screens.Events
import kotlinx.parcelize.Parcelize

/**
 * Root component for the sign up process
 */
interface SignUpProcessComponent {
    val child: Value<ChildStack<*, Events<*>>>
}

class DefaultSignUpProcess(
    context: ComponentContext
) : ComponentContext by context, SignUpProcessComponent {

    private val navigation = StackNavigation<Config>()

    override val child: Value<ChildStack<*, Events<*>>> = childStack(
        source = navigation,
        initialConfiguration = Config.SignUp,
        childFactory = { config: Config, context: ComponentContext ->
            when(config) {
                is Config.SignUp -> TODO()
                Config.EmailVerification -> TODO()
                Config.Profile -> TODO()
            }
        }
    )


    sealed interface Config: Parcelable {
        @Parcelize
        data object SignUp: Config
        @Parcelize
        data object Profile: Config
        @Parcelize
        data object EmailVerification: Config
    }
}