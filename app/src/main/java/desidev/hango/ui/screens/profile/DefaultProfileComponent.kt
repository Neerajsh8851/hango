package desidev.hango.ui.screens.profile

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.Gender
import desidev.kotlinutils.Option
import java.time.LocalDate

typealias OnSubmit = () -> Unit
typealias OnProfilePicSelect = (Uri) -> Unit
typealias OnNameEdit = (String) -> Unit
typealias OnGenderValueSelect = (Gender) -> Unit
typealias OnDateOfBirthValueSelect = (LocalDate) -> Unit


class DefaultProfileComponent(
    context: ComponentContext,
    override val name: Value<String>,
    override val dob: Value<LocalDate>,
    override val gender: Value<Gender>,
    override val profilePic: Value<Option<ImageBitmap>>,
    private val onNameEdit: OnNameEdit,
    private val onDobValueSelect: OnDateOfBirthValueSelect,
    private val onGenderValueSelect: OnGenderValueSelect,
    private val onProfilePicSelect: OnProfilePicSelect,
    private val onSubmit: OnSubmit
) : ProfileComponent,
    ComponentContext by context {

    override fun setName(name: String) {
        onNameEdit(name)
    }

    override fun setDob(dob: LocalDate) {
        onDobValueSelect(dob)
    }

    override fun setGender(gender: Gender) {
        onGenderValueSelect(gender)
    }

    override fun onPhotoSelect(profileUri: Uri) {
        onProfilePicSelect(profileUri)
    }

    override fun submit() {
        onSubmit()
    }
}