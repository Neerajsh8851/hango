/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.main

import SignedInComponent
import android.os.Parcelable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.screens.signedout.SignedOutComponent
import kotlinx.parcelize.Parcelize

interface MainComponent {
    val children: Value<ChildStack<Config, Child>>

    sealed interface Config: Parcelable {
        @Parcelize
        data object SignedOut: Config

        @Parcelize
        data object SignedIn: Config
    }

    sealed interface Child {
        data class SignedOutScreen(val component: SignedOutComponent): Child
        data class SignedInScreen(val component: SignedInComponent): Child
    }
}