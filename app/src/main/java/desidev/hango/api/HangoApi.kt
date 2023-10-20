package desidev.hango.api

import desidev.hango.api.model.JwtToken
import desidev.hango.api.model.UserCredentials
import desidev.hango.api.model.UserSignUp

interface HangoApi {
    suspend fun userSignIn(payload: UserCredentials): Result<JwtToken>
    suspend fun userSignUp(payload: UserSignUp): Result<Unit>
}