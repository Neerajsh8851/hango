/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.signedin

import SignedInComponent
import SignedInComponent.Child
import SignedInComponent.Config
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.screens.editprofile.DefaultEditProfileComponent
import desidev.hango.ui.screens.home.DefaultHomeComponent
import desidev.hango.ui.screens.myprofile.DefaultMyProfileComponent

class DefaultSignedInComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, SignedInComponent {

    private val navigator = StackNavigation<Config>()

    override val children: Value<ChildStack<Config, Child>>
        get() = childStack(
            source = navigator,
            initialConfiguration = Config.Home,
            childFactory = ::createChild
        )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): Child {
        return when (config) {
            is Config.Home -> Child.HomeScreen(
                component = DefaultHomeComponent(componentContext)
            )

            is Config.MyProfile -> Child.MyProfileScreen(
                component = DefaultMyProfileComponent(componentContext)
            )

            is Config.EditProfile -> Child.EdiProfileScreen(
                component = DefaultEditProfileComponent(componentContext)
            )
        }
    }
}