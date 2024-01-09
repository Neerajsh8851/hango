package desidev.hango.api.impl

import com.google.gson.GsonBuilder
import desidev.hango.api.HangoAuthApi
import desidev.hango.api.model.EmailAuthData
import desidev.hango.api.model.UserCredential
import desidev.kotlin.utils.Option
import desidev.kotlin.utils.Result
import desidev.kotlin.utils.ifSome
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
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
    ): Result<String, Exception> {

        val jsonPayload = GsonBuilder().create().run {
            toJson(
                mapOf(
                    "authId" to verifiedAuthId,
                    "credential" to credential,
                    "basicInfo" to userInfo
                )
            )
        }

        val response = client.submitFormWithBinaryData(
            url = "$baseUrl/account/create",
            formData = formData {
                append("jsonPayload", jsonPayload)
                pictureData.ifSome {
                    append("image", it.data, Headers.build {
                            append(HttpHeaders.ContentType, it.type.toString())
                        })
                    append(HttpHeaders.ContentDisposition, "filename=${it.originalFilename}")
                }
            }
        )

        if (response.status != HttpStatusCode.OK) {
            return Result.Err(IOException("failed with response: ${response.status}"))
        }

        return Result.Ok(response.bodyAsText())
    }
}