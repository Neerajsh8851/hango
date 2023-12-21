package desidev.hango.api.model

import desidev.hango.models.Gender
import java.time.LocalDate

data class Profile(
    val firstname: String,
    val lastname: String,
    val gender: Gender,
    val profileUrl: String?,
    val dateOfBirth: LocalDate
)