/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.screens.signedin.SignedInComponent
import desidev.hango.ui.screens.signedout.SignedOutComponent

interface MainComponent {
    val children: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class SignedOutScreen(val component: SignedOutComponent): Child
        data class SignedInScreen(val component: SignedInComponent): Child
    }
}
