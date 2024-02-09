package desidev.hango.api.model

data class SessionInfo(
    val sessionToken: String,
    val user: User
)
