/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value

class DefaultHomeComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext, HomeComponent {

    private val navigator = StackNavigation<HomeComponent.Config>()
    override fun onDiscoverTabSelect() {
        navigator.replaceCurrent(HomeComponent.Config.Discover)
    }

    override fun onFollowingTabSelect() {
        navigator.replaceCurrent(HomeComponent.Config.Following)
    }

    override val childTab: Value<ChildStack<HomeComponent.Config, HomeComponent.Child>>
        get() = childStack(
            source = navigator,
            initialConfiguration = HomeComponent.Config.Discover,
            childFactory = ::createChild
        )

    private fun createChild(
        config: HomeComponent.Config,
        componentContext: ComponentContext
    ): HomeComponent.Child {
        when (config) {
            HomeComponent.Config.Discover -> TODO()
            HomeComponent.Config.Following -> TODO()
        }
    }
}