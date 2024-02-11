/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.signedout

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.parcelable.Parcelable
import desidev.hango.api.AuthService
import desidev.hango.ui.screens.signedout.SignedOutComponent.Child
import desidev.hango.ui.screens.signin.DefaultSignInComponent
import desidev.hango.ui.screens.signin.OnUserSignedIn
import desidev.hango.ui.screens.signup.DefaultSignUpComponent
import kotlinx.parcelize.Parcelize

class DefaultSignedOutComponent(
    private val authService: AuthService,
    private val onUserSignedIn: OnUserSignedIn,
    componentContext: ComponentContext
) : SignedOutComponent, ComponentContext by componentContext {

    private val navigator = StackNavigation<Config>()

    override val children = childStack(
        source = navigator,
        handleBackButton = true,
        initialConfiguration = Config.SignInScreen,
        childFactory = ::createChild
    )

    private fun createChild(config: Config, componentContext: ComponentContext) = when (config) {

        is Config.SignInScreen -> Child.SignInScreen(
            component = DefaultSignInComponent(
                authService = authService,
                componentContext = componentContext,
                onUserSignIn = onUserSignedIn,
                onForgetPasswordClick = {},
                onSignupClick = {
                    navigator.push(Config.SignUpScreen)
                }
            )
        )

        is Config.SignUpScreen -> Child.SignUpScreen(
            component = DefaultSignUpComponent(
                componentContext = componentContext,
                authService = authService,
                onAccountCreated = {
                    onUserSignedIn()
                },
                onGoBack = {
                    navigator.pop()
                }
            )
        )
    }

    sealed class Config : Parcelable {
        @Parcelize
        data object SignUpScreen : Config()

        @Parcelize
        data object SignInScreen : Config()
    }
}