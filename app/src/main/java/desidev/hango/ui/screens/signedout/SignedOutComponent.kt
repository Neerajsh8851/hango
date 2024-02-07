/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.signedout

import android.os.Parcelable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.screens.signin.SigninComponent
import desidev.hango.ui.screens.signup.SignupComponent
import kotlinx.parcelize.Parcelize

interface SignedOutComponent {
    val children: Value<ChildStack<Config, Child>>
    sealed interface Config : Parcelable {
        @Parcelize
        data object Signup : Config
        @Parcelize
        data object Signin : Config
    }

    sealed interface Child {
        data class SignUpScreen(val component: SignupComponent) : Child
        data class SignInScreen(val component: SigninComponent) : Child
    }
}