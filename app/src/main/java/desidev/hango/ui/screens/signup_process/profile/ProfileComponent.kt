package desidev.hango.ui.screens.signup_process.profile

import android.net.Uri
import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.Gender
import desidev.kotlinutils.Option
import java.time.LocalDate



interface ProfileComponent  {
    val name: Value<String>
    val dob: Value<LocalDate>
    val gender: Value<Gender>
    val profilePic: Value<Option<Uri>>

    fun setName(name: String)
    fun setDob(dob: LocalDate)
    fun setGender(gender: Gender)
    fun onPhotoSelect(profileUri: Uri)

    fun onSubmitClick()
    fun interface OnUpdateNameCallback {
        fun onSetName(name: String)
    }

    fun interface OnUpdateDobCallback {
        fun onSetDOB(value: LocalDate)
    }

    fun interface OnUpdateGenderCallback {
        fun onSetGender(value: Gender)
    }

    fun interface OnProfilePhotoSelect {
        fun onPhotoSelect(uri: Uri)
    }
}

