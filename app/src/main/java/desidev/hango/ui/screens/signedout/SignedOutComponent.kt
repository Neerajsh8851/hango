/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.signedout

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.screens.signin.SignInComponent
import desidev.hango.ui.screens.signup.SignupComponent

interface SignedOutComponent {
    val children: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class SignUpScreen(val component: SignupComponent) : Child
        data class SignInScreen(val component: SignInComponent) : Child
    }
}