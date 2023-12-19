package desidev.hango.models

data class User(
    val id: String,
    val first: String,
    val lastname: String,
    val token: String,
    val gender: Gender
)


enum class Gender { Male, Female, Other }