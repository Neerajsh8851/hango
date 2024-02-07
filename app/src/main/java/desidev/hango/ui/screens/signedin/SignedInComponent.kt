/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */


import android.os.Parcelable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.screens.editprofile.EditProfileComponent
import desidev.hango.ui.screens.home.HomeComponent
import desidev.hango.ui.screens.myprofile.MyProfileComponent
import kotlinx.parcelize.Parcelize


interface SignedInComponent {

    val children: Value<ChildStack<Config, Child>>
    sealed interface Config : Parcelable {
        @Parcelize
        data object Home : Config
        @Parcelize
        data object MyProfile : Config

        @Parcelize
        data object EditProfile : Config
    }

    sealed interface Child {
        data class HomeScreen(val component: HomeComponent) : Child
        data class MyProfileScreen(val component: MyProfileComponent) : Child
        data class EdiProfileScreen(val component: EditProfileComponent) : Child
    }
}