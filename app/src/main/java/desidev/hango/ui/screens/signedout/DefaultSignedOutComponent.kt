/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.signedout

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import desidev.hango.ui.screens.signin.DefaultSignInComponent
import desidev.hango.ui.screens.signup.DefaultSignUpComponent

class DefaultSignedOutComponent(
    componentContext: ComponentContext
): SignedOutComponent, ComponentContext by componentContext{

    private val navigator = StackNavigation<SignedOutComponent.Config>()

    override val children = childStack(
        source = navigator,
        key = "SignedOutComponent",
        initialConfiguration = SignedOutComponent.Config.Signup,
        childFactory = ::createChild
    )

    private fun createChild(
        config: SignedOutComponent.Config,
        componentContext: ComponentContext
    ): SignedOutComponent.Child = when(config) {
        is SignedOutComponent.Config.Signup -> SignedOutComponent.Child.SignUpScreen(
            component = DefaultSignUpComponent(
                componentContext = componentContext,
                authService = TODO(),
                onAccountCreated = {}
            )
        )

        is SignedOutComponent.Config.Signin -> SignedOutComponent.Child.SignInScreen(
            component = DefaultSignInComponent(
                componentContext = componentContext,
                onSignupClick = {},
                onForgetPasswordClick = {},
                onSigninClick = {}
            )
        )
    }

}