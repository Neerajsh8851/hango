package desidev.hango.compmodel

import android.util.Log
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.api.HangoAuthApi
import desidev.hango.api.model.EmailAuthData
import desidev.hango.api.model.UserCredential
import desidev.hango.model.User
import desidev.kotlin.utils.Option
import desidev.kotlin.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AuthCompModel {
    val user: Value<Option<User>>
    fun getLoginToken(): Option<String>
    suspend fun login(credential: UserCredential)
    fun logout()
    suspend fun requestEmailAuth(
        email: String,
        purpose: EmailAuthData.Purpose,
    ): Result<EmailAuthData, Exception>

    suspend fun verifyEmailAuth(authId: String, otp: String): Result<EmailAuthData, Exception>
    suspend fun createAccount(
        verifiedAuthId: String,
        credential: UserCredential,
        userInfo: HangoAuthApi.BasicUserInfo,
        pictureData: Option<HangoAuthApi.PictureData>,
    )
}


class DefaultAuthCompModel(
    private val authApi: HangoAuthApi,
) : AuthCompModel {
    companion object {
        val TAG = DefaultAuthCompModel::class.simpleName
    }

    private var token: Option<String> = Option.None

    override val user: Value<Option<User>> = MutableValue(Option.None)
    override fun getLoginToken(): Option<String> = token
    override suspend fun login(credential: UserCredential) {
        when (val result = authApi.login(credential)) {
            is Result.Err -> {
                Log.d(TAG, "login result: ${result.err} ")
            }

            is Result.Ok -> {
                Log.d(TAG, "login result: ${result.value}")
                token = Option.Some(result.value)
            }
        }
    }

    override fun logout() {
        token = Option.None
    }

    override suspend fun requestEmailAuth(
        email: String,
        purpose: EmailAuthData.Purpose,
    ): Result<EmailAuthData, Exception> {
        return withContext(Dispatchers.IO) {
            authApi.createEmailAuth(email, purpose)
        }
    }

    override suspend fun verifyEmailAuth(
        authId: String,
        otp: String,
    ): Result<EmailAuthData, Exception> {
        return withContext(Dispatchers.IO) { authApi.verifyEmailAuth(otp, authId) }
    }

    override suspend fun createAccount(
        verifiedAuthId: String,
        credential: UserCredential,
        userInfo: HangoAuthApi.BasicUserInfo,
        pictureData: Option<HangoAuthApi.PictureData>,
    ): Unit = withContext(Dispatchers.IO) {
        val result = authApi.registerNewAccount(
            verifiedAuthId = verifiedAuthId,
            credential = credential,
            userInfo = userInfo,
            pictureData = pictureData
        )

        when (result) {
            is Result.Err -> {
                Log.d(TAG, "createAccount: result: ${result.err}")
            }

            is Result.Ok -> {
                Log.d(TAG, "createAccount: result: ${result.value}")
            }
        }
    }
}