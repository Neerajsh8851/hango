/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.signedout

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import desidev.hango.ui.screens.signin.SignInContent
import desidev.hango.ui.screens.signup.SignUpContent

@Composable
fun SignedOutContent(component: SignedOutComponent) {
    Children(stack = component.children) {
      when(val child = it.instance) {
          is SignedOutComponent.Child.SignInScreen -> SignInContent(child.component)
          is SignedOutComponent.Child.SignUpScreen -> SignUpContent(child.component)
      }
    }
}