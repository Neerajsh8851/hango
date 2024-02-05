package desidev.hango.ui.screens.myprofile

import desidev.hango.api.model.Gender
import desidev.kotlinutils.Option

interface MyProfileComponent {
    interface Model {
        val name: String
        val email: String
        val gender: Gender
        val dateOfBirth: String
        val age: Int
        val rating: Float
        val profilePic: Option<String>
        val status: String
        val followers: Int
        val following: Int
        val aboutMe: String
    }

    val model: Model
}