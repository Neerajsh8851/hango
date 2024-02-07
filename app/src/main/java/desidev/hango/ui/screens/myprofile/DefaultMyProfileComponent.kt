/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.myprofile

import com.arkivanov.decompose.ComponentContext

class DefaultMyProfileComponent (
    componentContext: ComponentContext,
): ComponentContext by componentContext, MyProfileComponent {

    override val model: MyProfileComponent.Model = TODO()
}