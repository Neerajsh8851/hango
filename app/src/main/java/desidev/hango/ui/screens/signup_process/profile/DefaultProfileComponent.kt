package desidev.hango.ui.screens.signup_process.profile

import android.net.Uri
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.Gender
import desidev.hango.ui.screens.signup_process.profile.ProfileComponent.*
import desidev.hango.ui.screens.signup_process.signup.OnSubmitClick
import desidev.kotlin.utils.Option
import java.time.LocalDate

class DefaultProfileComponent(
    context: ComponentContext,
    override val name: Value<String>,
    override val dob: Value<LocalDate>,
    override val gender: Value<Gender>,
    override val profilePic: Value<Option<Uri>>,
    private val nameCallback: OnUpdateNameCallback,
    private val dobCallback: OnUpdateDobCallback,
    private val genderCallback: OnUpdateGenderCallback,
    private val profileUrlCallback: OnProfilePhotoSelect,
    private val onSubmitClick: OnSubmitClick
) : ProfileComponent,
    ComponentContext by context {

    override fun setName(name: String) {
        nameCallback.onSetName(name)
    }

    override fun setDob(dob: LocalDate) {
        dobCallback.onSetDOB(dob)
    }

    override fun setGender(gender: Gender) {
        genderCallback.onSetGender(gender)
    }

    override fun onPhotoSelect(profileUri: Uri) {
        profileUrlCallback.onPhotoSelect(profileUri)
    }

    override fun onSubmitClick() {
        onSubmitClick.onSubmit()
    }
}