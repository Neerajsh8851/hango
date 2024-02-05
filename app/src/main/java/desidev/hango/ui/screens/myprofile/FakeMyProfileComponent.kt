package desidev.hango.ui.screens.myprofile

import desidev.hango.api.model.Gender
import desidev.kotlinutils.Option

class FakeMyProfileComponent : MyProfileComponent {
    override val model: MyProfileComponent.Model
        get() = object : MyProfileComponent.Model {
            override val name = "John Doe"
            override val email = "example@email.com"
            override val gender: Gender
                get() = Gender.Female
            override val rating: Float = 4.5f
            override val profilePic: Option<String>
                get() = Option.Some("https://images.pexels.com/photos/4917824/pexels-photo-4917824.jpeg?auto=compress&cs=tinysrgb&w=600")
            override val status: String
                get() = "Active"
            override val followers: Int
                get() = 100
            override val following: Int
                get() = 12
            override val aboutMe: String
                get() = "I am a software developer"

            override val dateOfBirth: String = "01/01/2000"
            override val age: Int
                get() = 21
        }
}