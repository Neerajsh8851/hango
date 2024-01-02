package desidev.hango.api.impl

import desidev.hango.api.HangoAuthApi
import desidev.hango.api.model.EmailAuthPurpose
import desidev.hango.api.model.EmailAuthResponse
import desidev.hango.api.model.RegisterAccountPayload
import desidev.hango.api.model.UserCredential
import desidev.hango.api.model.VerifyEmailAuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.CancellationException
import desidev.kotlin.utils.Result

class DefaultHangoApi(private val baseUrl: String) : HangoAuthApi {
    private val client by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                gson {}
            }
        }
    }
    override suspend fun userSignIn(payload: UserCredential): Result<String, Exception> {
       try {
           val response = client.post("$baseUrl/user/sign-in") {
               contentType(ContentType.Application.Json)
               setBody(payload)
           }
           return if (response.status == HttpStatusCode.OK) {
               val jwtToken = response.body<String>()
               Result.Ok(jwtToken)
           } else {
               throw RuntimeException("Invalid credentials!")
           }
       } catch (ex: Exception) {
           if (ex is CancellationException) throw ex
           return Result.Err(ex)
       }
    }

    override suspend fun registerNewAccount(
        payload: RegisterAccountPayload,
        authDataId: String,
    ): Result<Nothing, String> {
        TODO("Not yet implemented")
    }

    override suspend fun createEmailAuth(
        emailAddress: String,
        purpose: EmailAuthPurpose,
    ): EmailAuthResponse {
        TODO("Not yet implemented")
    }

    override suspend fun verifyEmailAuth(
        otpValue: String,
        authId: String,
    ): VerifyEmailAuthResponse {
        TODO("Not yet implemented")
    }
}