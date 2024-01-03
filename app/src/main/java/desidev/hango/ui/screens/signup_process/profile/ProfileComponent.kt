package desidev.hango.ui.screens.signup_process.profile

import android.net.Uri
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.model.Gender
import desidev.kotlin.utils.Option
import java.time.LocalDate



interface ProfileComponent  {
    val name: Value<String>
    val dob: Value<LocalDate>
    val gender: Value<Gender>
    val profilePic: Value<Option<Uri>>

    fun setName(name: String)
    fun setDob(dob: LocalDate)
    fun setGender(gender: Gender)
    fun onProfileSelect(profileUri: Uri)
}


fun interface OnUpdateNameCallback {
    fun onUpdateName(name: String)
}

fun interface OnUpdateDobCallback {
    fun onUpdateDob(value: LocalDate)
}

fun interface OnUpdateGenderCallback {
    fun onUpdateGender(value: Gender)
}

fun interface OnProfilePhotoSelect {
    fun onPhotoSelect(uri: Uri)
}

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
) : ProfileComponent,
    ComponentContext by context {

    override fun setName(name: String) {
        nameCallback.onUpdateName(name)
    }

    override fun setDob(dob: LocalDate) {
        dobCallback.onUpdateDob(dob)
    }

    override fun setGender(gender: Gender) {
        genderCallback.onUpdateGender(gender)
    }

    override fun onProfileSelect(profileUri: Uri) {
        profileUrlCallback.onPhotoSelect(profileUri)
    }
}

class FakeProfileComponent : ProfileComponent {

    private val _name = MutableValue("")
    override val name: Value<String> = _name

    private val _dob = MutableValue(LocalDate.now().minusYears(18))
    override val dob: Value<LocalDate> = _dob

    private val _gender = MutableValue(Gender.Male)
    override val gender: Value<Gender> = _gender

    private val _profilePic: MutableValue<Option<Uri>> = MutableValue(Option.None)
    override val profilePic: Value<Option<Uri>> =_profilePic

    override fun setName(name: String) { _name.value = name }

    override fun setDob(dob: LocalDate) {
        _dob.value = dob
    }

    override fun setGender(gender: Gender) {
        _gender.value = gender
    }

    override fun onProfileSelect(profileUri: Uri) {

    }
}