package desidev.hango.api.model

import java.time.LocalDate

data class BasicInfo(
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
)