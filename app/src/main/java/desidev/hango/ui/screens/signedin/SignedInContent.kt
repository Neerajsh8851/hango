/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.signedin

import SignedInComponent
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import desidev.hango.ui.screens.editprofile.EditProfileContent
import desidev.hango.ui.screens.home.HomeContent
import desidev.hango.ui.screens.myprofile.MyProfileContent

@Composable
fun SignedInContent(component: SignedInComponent) {
    Children(stack =component.children) {
        when(val child = it.instance) {
            is SignedInComponent.Child.HomeScreen -> HomeContent(child.component)
            is SignedInComponent.Child.MyProfileScreen -> MyProfileContent(component =child.component)
            is SignedInComponent.Child.EdiProfileScreen -> EditProfileContent(component =child.component)
        }
    }
}