package desidev.hango.ui.screens.editprofile

import android.net.Uri
import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.Gender
import desidev.kotlinutils.Option
import java.time.LocalDate

interface EditProfileComponent {
    data class Model(
        val name: Value<String>,
        val gender: Value<Gender>,
        val birthDate: Value<LocalDate>,
        val aboutMe: Value<String>,
        val profilePic: Value<Option<String>>
    )

    val model: Model

    fun setName(name: String)
    fun setGender(gender: Gender)
    fun setDateOfBirth(date: LocalDate)
    fun updateAboutMe(aboutMe: String)
    fun changeProfilePic(uri: Uri)
}


