/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.main

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import desidev.hango.ui.screens.signedin.SignedInContent
import desidev.hango.ui.screens.signedout.SignedOutContent

@Composable
fun MainContent(component: MainComponent) {
    Children(
        stack = component.children,
    ) {
        when (val child = it.instance) {
            is MainComponent.Child.SignedOutScreen -> {
                 SignedOutContent(child.component)
            }
            is MainComponent.Child.SignedInScreen -> {
                 SignedInContent(child.component)
            }
        }
    }
}