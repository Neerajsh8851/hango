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
import desidev.kotlinutils.Option
import desidev.kotlinutils.Result
import desidev.kotlinutils.Result.Err
import desidev.kotlinutils.Result.Ok
import desidev.kotlinutils.ifSome
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
import io.ktor.util.InternalAPI
import kotlinx.coroutines.CancellationException
import java.io.File
import java.io.IOException
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Default Implementation of authService
 *
 * @param basedir the base directory in which it stores the session data
 *
 */

class DefaultAuthService(
    private val basedir: File
) : AuthService {
    private val baseUrl = "http://139.59.85.69:9080"

    private val sessionStore: SessionStore by lazy {
        if (!basedir.canWrite()) {
            throw IOException("basedir $basedir is not writable")
        }

        if (!basedir.exists()) {
            basedir.mkdirs()
        }

        DefaultSessionStore.init(basedir)
        DefaultSessionStore.getInstance()
    }


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

    override suspend fun login(payload: UserCredential): Result<SessionInfo, LoginError> {
        return try {
            val response = client.post("$baseUrl/user/sign-in") {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }

            if (response.status == HttpStatusCode.OK) {
                val loginResult = response.body<LoginResult>()
                if (loginResult.status == LoginResult.Status.VALID) {
                    // save the login information
                    sessionStore.saveSession(loginResult.data!!)
                    Ok(loginResult.data)

                } else {
                    Err(LoginError.InvalidCredential)
                }
            } else {
                Err(
                    LoginError.Error(
                        IOException("failed with response: ${response.status}: ${response.bodyAsText()}")
                    )
                )
            }
        } catch (ex: Exception) {
            if (ex is CancellationException) throw ex
            Err(
                LoginError.Error(
                    IOException("failed with exception: $ex")
                )
            )
        }
    }

    override suspend fun requestEmailAuth(
        emailAddress: String,
        purpose: EmailAuthData.Purpose,
    ): Result<EmailAuthData, EmailAuthFailure> {
        val response = client.get("$baseUrl/email-auth/create") {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "email" to emailAddress, "purpose" to purpose.name
                )
            )
        }

        if (response.status != HttpStatusCode.OK) {
            return Err(
                EmailAuthFailure.EmailAuthCreationFailed(
                    response.status.toString(),
                    response.bodyAsText()
                )
            )
        }

        val responseData = try {
            response.body<EmailAuthData>()
        } catch (ex: NoTransformationFoundException) {
            return Err(
                EmailAuthFailure.EmailAuthErr(
                    IOException("failed with exception: $ex")
                )
            )
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
                    "authId" to authId, "otp" to otpValue
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


    @OptIn(InternalAPI::class)
    override suspend fun registerNewAccount(
        verifiedAuthId: String,
        credential: UserCredential,
        userInfo: BasicInfo,
        pictureData: Option<PictureData>,
    ): Result<SessionInfo, Exception> {
        return try {
            val jsonPayload = GsonBuilder().registerTypeAdapter(
                LocalDateTime::class.java,
                LocalDateTimeTypeAdapter()
            ).registerTypeAdapter(LocalDate::class.java, LocalDateTypeAdapter()).create()
                .toJson(
                    mapOf(
                        "authId" to verifiedAuthId,
                        "credential" to credential,
                        "basicInfo" to userInfo
                    )
                )

            val response = client.submitFormWithBinaryData(url = "$baseUrl/account/create",
                formData = formData {
                    append("jsonPayload", jsonPayload)
                    pictureData.ifSome { pictureData ->
                        append("image", pictureData.data, Headers.build {
                            append(HttpHeaders.ContentType, pictureData.type)
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=${pictureData.originalFilename}"
                            )
                        })
                    }
                })

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

    override suspend fun getCurrentLoginSession(): Option<SessionInfo> {
        return sessionStore.getSession()
    }

    override suspend fun logout() {
        sessionStore.clearSession()
    }
}