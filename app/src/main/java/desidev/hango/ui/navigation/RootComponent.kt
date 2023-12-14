package desidev.hango.ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize

class RootComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    private val navigation = StackNavigation<Configuration>()

    val stack = childStack<Configuration, Child>(
        source = navigation,
        initialConfiguration = Configuration.ScreenAConfig,
        handleBackButton = true,
        childFactory = ::childFactory
    )


    @OptIn(ExperimentalDecomposeApi::class)
    private fun childFactory(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child {
        return when (configuration) {
            is Configuration.ScreenAConfig -> Child.ScreenA(
                ScreenAComponent(
                    componentContext,
                    onNavigateToScreenB = { message ->
                        navigation.pushNew(Configuration.ScreenBConfig(message))
                    }
                )
            )

            is Configuration.ScreenBConfig -> Child.ScreenB(
                ScreenBComponent(
                    componentContext,
                    configuration.message,
                    onGoBack = {
                        navigation.pop()
                    }
                )
            )
        }
    }

    sealed class Child {
        data class ScreenA(val screenAComponent: ScreenAComponent) : Child()
        data class ScreenB(val screenBComponent: ScreenBComponent) : Child()
    }



    sealed class Configuration : Parcelable {

        @Parcelize
        data object ScreenAConfig : Configuration()

        @Parcelize
        class ScreenBConfig(val message: String) : Configuration()
    }
}

