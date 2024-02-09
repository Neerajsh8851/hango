/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import desidev.hango.api.AuthService
import desidev.hango.ui.screens.main.MainComponent.Child
import desidev.hango.ui.screens.main.MainComponent.Config
import desidev.hango.ui.screens.signedin.DefaultSignedInComponent
import desidev.hango.ui.screens.signedout.DefaultSignedOutComponent
import desidev.kotlinutils.isSome
import kotlinx.coroutines.runBlocking

class DefaultMainComponent(
    componentContext: ComponentContext,
    private val authService: AuthService
) : MainComponent,
    ComponentContext by componentContext {
    private val navigator = StackNavigation<Config>()

    private val currentSession by lazy {
        runBlocking {
            authService.getCurrentLoginSession()
        }
    }

    private val initialConfig: Config get() = if (currentSession.isSome()) Config.SignedIn else Config.SignedOut

    override val children = childStack(
        source = navigator,
        key = "MainComponent",
        initialConfiguration = initialConfig,
        childFactory = ::createChild,
    )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): Child =
        when (config) {
            is Config.SignedOut -> Child.SignedOutScreen(
                component = DefaultSignedOutComponent(
                    componentContext = componentContext,
                    authService = authService,
                    onUserSignedIn = {
                        navigator.replaceCurrent(Config.SignedIn)
                    }
                )
            )

            is Config.SignedIn -> Child.SignedInScreen(
                component = DefaultSignedInComponent(
                    componentContext = componentContext,
                )
            )
        }
}