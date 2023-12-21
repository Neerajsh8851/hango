package desidev.hango.api.model

data class RegisterAccountPayload(
    val credential: UserCredential,
    val profile: Profile
)
