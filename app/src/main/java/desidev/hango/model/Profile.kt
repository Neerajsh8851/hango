package desidev.hango.model

import java.time.LocalDate

data class Profile(
    val firstname: String,
    val lastname: String,
    val gender: Gender,
    val profileUrl: String?,
    val dateOfBirth: LocalDate,
    val emailAddress: String
)