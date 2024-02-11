package desidev.hango.ui.screens.profile

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.Gender
import desidev.kotlinutils.Option
import java.time.LocalDate

class FakeProfileComponent : ProfileComponent {

    private val _name = MutableValue("")
    override val name: Value<String> = _name

    private val _dob = MutableValue(LocalDate.now().minusYears(18))
    override val dob: Value<LocalDate> = _dob

    private val _gender = MutableValue(Gender.Male)
    override val gender: Value<Gender> = _gender

    private val _profilePic: MutableValue<Option<ImageBitmap>> = MutableValue(Option.None)
    override val profilePic: Value<Option<ImageBitmap>> =_profilePic

    override fun setName(name: String) { _name.value = name }

    override fun setDob(dob: LocalDate) {
        _dob.value = dob
    }

    override fun setGender(gender: Gender) {
        _gender.value = gender
    }

    override fun onPhotoSelect(profileUri: Uri) {

    }

    override fun submit() {
        TODO("Not yet implemented")
    }
}