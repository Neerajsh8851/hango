package desidev.hango.api.impl

import desidev.hango.api.HangoAuthApi
import desidev.hango.api.model.EmailAuthData
import desidev.hango.api.model.UserCredential
import desidev.kotlin.utils.Option
import desidev.kotlin.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.CancellationException
import java.io.IOException
import java.time.Duration

class DefaultHangoApi(private val baseUrl: String) : HangoAuthApi {
    private val client by lazy {
        HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = Duration.ofSeconds(15).toMillis()
            }
            install(ContentNegotiation) {
                gson()
            }
        }
    }

    override suspend fun login(payload: UserCredential): Result<String, Exception> {
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

    override suspend fun createEmailAuth(
        emailAddress: String,
        purpose: EmailAuthData.Purpose,
    ): Result<EmailAuthData, Exception> {
        val response = client.get("$baseUrl/email-auth/create") {
            parameter("email", emailAddress)
            parameter("purpose", purpose.name)
        }

        if (response.status != HttpStatusCode.OK) {
            return Result.Err(IOException("failed with response: ${response.status}"))
        }

        val responseData = try {
            response.body<EmailAuthData>()
        } catch (ex: NoTransformationFoundException) {
            return Result.Err(ex)
        }

        return Result.Ok(responseData)
    }

    override suspend fun verifyEmailAuth(
        otpValue: String,
        authId: String,
    ): Result<EmailAuthData, Exception> {
        val response = client.post("${baseUrl}/email-auth/verify") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("otpValue" to otpValue, "authId" to authId))
        }

        if (response.status != HttpStatusCode.OK) {
            return Result.Err(IOException("failed with response: ${response.status}"))
        }

        val data: EmailAuthData = try {
            response.body()
        } catch (ex: NoTransformationFoundException) {
            return Result.Err(ex)
        }

        return Result.Ok(data)
    }

    override suspend fun registerNewAccount(
        verifiedAuthId: String,
        credential: UserCredential,
        userInfo: HangoAuthApi.BasicUserInfo,
        pictureData: Option<HangoAuthApi.PictureData>,
    ): Result<Nothing, String> {
        TODO("Not yet implemented")
    }
}