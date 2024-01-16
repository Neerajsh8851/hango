package desidev.hango.api

import com.google.gson.GsonBuilder
import desidev.hango.api.model.BasicInfo
import desidev.hango.api.model.EmailAuthData
import desidev.hango.api.model.LoginResult
import desidev.hango.api.model.PictureData
import desidev.hango.api.model.SessionInfo
import desidev.hango.api.model.UserCredential
import desidev.hango.api.typeadapter.LocalDateTimeTypeAdapter
import desidev.hango.api.typeadapter.LocalDateTypeAdapter
import desidev.kotlin.utils.Option
import desidev.kotlin.utils.Result
import desidev.kotlin.utils.Result.*
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
import java.time.LocalDate
import java.time.LocalDateTime

class DefaultAuthService(private val baseUrl: String) : HangoAuthService {
    private val client by lazy {
        HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = Duration.ofSeconds(15).toMillis()
            }
            install(ContentNegotiation) {
                gson {
                    registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
                    registerTypeAdapter(LocalDate::class.java, LocalDateTypeAdapter())
                }
            }
        }
    }

    override suspend fun login(payload: UserCredential): Result<LoginResult, Exception> {
        try {
            val response = client.post("$baseUrl/user/sign-in") {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }
            return if (response.status == HttpStatusCode.OK) {
                val jwtToken = response.body<LoginResult>()
                Ok(jwtToken)
            } else {
                Err(IOException("failed with response: ${response.status}: msg: ${response.bodyAsText()}"))
            }
        } catch (ex: Exception) {
            if (ex is CancellationException) throw ex
            return Err(ex)
        }
    }

    override suspend fun requestEmailAuth(
        emailAddress: String,
        purpose: EmailAuthData.Purpose,
    ): Result<EmailAuthData, Exception> {
        val response = client.get("$baseUrl/email-auth/create") {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "email" to emailAddress,
                    "purpose" to purpose.name
                )
            )
        }

        if (response.status != HttpStatusCode.OK) {
            return Err(IOException("failed with response: ${response.status} ${response.bodyAsText()}"))
        }

        val responseData = try {
            response.body<EmailAuthData>()
        } catch (ex: NoTransformationFoundException) {
            return Err(ex)
        }

        return Ok(responseData)
    }

    override suspend fun verifyEmailAuth(
        authId: String,
        otpValue: String,
    ): Result<EmailAuthData, Exception> {
        val response = client.post("${baseUrl}/email-auth/verify") {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "authId" to authId,
                    "otp" to otpValue
                )
            )
        }

        if (response.status != HttpStatusCode.OK) {
            return Err(IOException("failed with response: ${response.status}: ${response.bodyAsText()}"))
        }

        val data: EmailAuthData = try {
            response.body()
        } catch (ex: NoTransformationFoundException) {
            return Err(ex)
        }

        return Ok(data)
    }

    override suspend fun registerNewAccount(
        verifiedAuthId: String,
        credential: UserCredential,
        userInfo: BasicInfo,
        pictureData: Option<PictureData>,
    ): Result<SessionInfo, Exception> {
        return try {
            val jsonPayload = GsonBuilder().create().toJson(
                mapOf(
                    "authId" to verifiedAuthId,
                    "credential" to credential,
                    "basicInfo" to userInfo
                )
            )

            val response = client.submitFormWithBinaryData(
                url = "$baseUrl/account/create",
                formData = formData {
                    append("jsonPayload", jsonPayload)
                    pictureData.ifSome {
                        append("image", it.data, Headers.build {
                            append(HttpHeaders.ContentType, it.type.toString())
                        })
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=${it.originalFilename}"
                        )
                    }
                }
            )

            if (response.status != HttpStatusCode.OK) {
                throw IOException("failed with response: ${response.status} ${response.bodyAsText()}")
            }

            Ok(response.body())

        } catch (ex: IOException) {
            Err(ex)
        } catch (ex: NoTransformationFoundException) {
            Err(ex)
        } catch (ex: CancellationException) {
            throw ex
        }
    }
}