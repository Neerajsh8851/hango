/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango.ui.screens.editprofile

import android.net.Uri
import com.arkivanov.decompose.ComponentContext
import desidev.hango.api.model.Gender
import java.time.LocalDate

class DefaultEditProfileComponent(
    componentContext: ComponentContext,
): ComponentContext by componentContext, EditProfileComponent {
    override val model: EditProfileComponent.Model
        get() = TODO()

    override fun setName(name: String) {
        TODO("Not yet implemented")
    }

    override fun setGender(gender: Gender) {
        TODO("Not yet implemented")
    }

    override fun setDateOfBirth(date: LocalDate) {
        TODO("Not yet implemented")
    }

    override fun updateAboutMe(aboutMe: String) {
        TODO("Not yet implemented")
    }

    override fun changeProfilePic(uri: Uri) {
        TODO("Not yet implemented")
    }
}