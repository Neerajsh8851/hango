package desidev.hango.api.model

import desidev.hango.model.Profile

data class RegisterAccountPayload(
    val credential: UserCredential,
    val profile: Profile
)
