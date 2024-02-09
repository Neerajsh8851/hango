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
import desidev.hango.ui.screens.discover.DefaultDiscoverComponent
import desidev.hango.ui.screens.following.DefaultFollowingComponent
import desidev.hango.ui.screens.home.HomeComponent.Child
import desidev.hango.ui.screens.home.HomeComponent.Config

class DefaultHomeComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext, HomeComponent {

    private val navigator = StackNavigation<Config>()
    override val childTab: Value<ChildStack<Config, Child>>
        get() = childStack(
            source = navigator,
            initialConfiguration = Config.Discover,
            childFactory = ::createChild
        )


    private fun createChild(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            Config.Discover -> Child.DiscoverScreen(
                component = DefaultDiscoverComponent(
                    componentContext = componentContext
                )
            )

            Config.Following -> Child.FollowingScreen(
                component = DefaultFollowingComponent(
                    componentContext = componentContext
                )
            )
        }


    override fun onDiscoverTabSelect() {
        navigator.replaceCurrent(Config.Discover)
    }

    override fun onFollowingTabSelect() {
        navigator.replaceCurrent(Config.Following)
    }
}