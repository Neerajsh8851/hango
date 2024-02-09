/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.signedin

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.screens.editprofile.DefaultEditProfileComponent
import desidev.hango.ui.screens.home.DefaultHomeComponent
import desidev.hango.ui.screens.myprofile.DefaultMyProfileComponent
import desidev.hango.ui.screens.signedin.SignedInComponent.Child
import desidev.hango.ui.screens.signedin.SignedInComponent.Config

class DefaultSignedInComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, SignedInComponent {

    private val navigator = StackNavigation<Config>()

    override val children: Value<ChildStack<Config, Child>>
        get() = childStack(
            source = navigator,
            initialConfiguration = Config.HomeScreen,
            childFactory = ::createChild
        )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): Child {
        return when (config) {
            is Config.HomeScreen -> Child.HomeScreen(
                component = DefaultHomeComponent(
                    componentContext = componentContext
                )
            )

            is Config.MyProfileScreen -> Child.MyProfileScreen(
                component = DefaultMyProfileComponent(
                    componentContext = componentContext
                )
            )

            is Config.EditProfileScreen -> Child.EdiProfileScreen(
                component = DefaultEditProfileComponent(
                    componentContext = componentContext
                )
            )
        }
    }
}

