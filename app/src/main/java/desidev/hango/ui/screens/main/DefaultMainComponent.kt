/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import desidev.hango.ui.screens.signedin.DefaultSignedInComponent
import desidev.hango.ui.screens.signedout.DefaultSignedOutComponent

class DefaultMainComponent(componentContext: ComponentContext) : MainComponent,
    ComponentContext by componentContext {
    private val navigator = StackNavigation<MainComponent.Config>()

    override val children = childStack(
        source = navigator,
        key = "MainComponent",
        initialConfiguration = MainComponent.Config.SignedOut,
        childFactory = ::createChild,
    )

    private fun createChild(
        config: MainComponent.Config,
        componentContext: ComponentContext
    ): MainComponent.Child =
        when (config) {
            is MainComponent.Config.SignedOut -> MainComponent.Child.SignedOutScreen(
                component = DefaultSignedOutComponent(
                    componentContext = componentContext,
                )
            )

            is MainComponent.Config.SignedIn -> MainComponent.Child.SignedInScreen(
                component = DefaultSignedInComponent(
                    componentContext = componentContext,
                )
            )
        }
}