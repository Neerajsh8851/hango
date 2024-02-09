package desidev.hango.ui.screens.myprofile

import desidev.hango.api.model.Gender
import desidev.kotlinutils.Option
import java.time.LocalDate

class FakeMyProfileComponent : MyProfileComponent {
    override val model: MyProfileComponent.Model = MyProfileComponent.Model(
        name = "John Doe",
        email = "johndoe@gmail",
        gender = Gender.Male,
        birthDate = LocalDate.of(1999, 1, 1),
        age = 22,
        rating = 4.5f,
        profilePic = Option.Some("https://example.com/johndoe.jpg"),
        status = "Active",
        followers = 100,
        following = 200,
        aboutMe = "I'm a software developer"
    )
}