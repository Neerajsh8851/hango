package desidev.hango.ui.screens.editprofile

import android.net.Uri
import com.arkivanov.decompose.value.MutableValue
import desidev.hango.api.model.Gender
import desidev.hango.ui.post
import desidev.kotlinutils.Option
import java.time.LocalDate

class FakeEditProfileComponent : EditProfileComponent {
    private val name = MutableValue("John Doe")
    private val gender = MutableValue(Gender.Female)
    private val dateOfBirth = MutableValue(LocalDate.of(2000, 1, 1))
    private val aboutMe = MutableValue("I am a software developer")
    private val profilePic = MutableValue(Option.Some("https://images.pexels.com/photos/4917824/pexels-photo-4917824.jpeg?auto=compress&cs=tinysrgb&w=600"))
    override val model: EditProfileComponent.Model
        get() = EditProfileComponent.Model(name, gender, dateOfBirth, aboutMe, profilePic)

    override fun setName(name: String) = this.name.post(name)

    override fun setGender(gender: Gender) = this.gender.post(gender)

    override fun setDateOfBirth(date: LocalDate) = this.dateOfBirth.post(date)

    override fun updateAboutMe(aboutMe: String) = this.aboutMe.post(aboutMe)

    override fun changeProfilePic(uri: Uri) {

    }
}