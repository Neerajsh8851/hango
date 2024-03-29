package desidev.hango.ui.screens.signup.account

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.api.HangoAuthService
import desidev.hango.api.model.BasicInfo
import desidev.hango.api.model.EmailAuthData
import desidev.hango.api.model.Gender
import desidev.hango.api.model.PictureData
import desidev.hango.api.model.SessionInfo
import desidev.hango.api.model.UserCredential
import desidev.hango.ui.componentScope
import desidev.hango.ui.post
import desidev.hango.ui.screens.signup.account.AccountComponent.AccountCreateStatus
import desidev.hango.ui.screens.signup.account.AccountComponent.AccountCreateStatus.*
import desidev.hango.ui.screens.signup.account.AccountComponent.OtpStatus
import desidev.kotlinutils.Option
import desidev.kotlinutils.Option.Some
import desidev.kotlinutils.Result
import desidev.kotlinutils.ifSome
import desidev.kotlinutils.isSome
import desidev.kotlinutils.unwrap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

typealias OnAccountCreated = (SessionInfo) -> Unit


class DefaultAccountComponent(
    context: ComponentContext,
    private val authService: HangoAuthService,
    override val userEmail: Value<String>,
    private val userPassword: Value<String>,
    private val name: Value<String>,
    private val gender: Value<Gender>,
    private val dob: Value<LocalDate>,
    private val pictureData: Option<PictureData>,
    private val onAccountCreated: OnAccountCreated
) : ComponentContext by context, AccountComponent {

    companion object {
        val TAG = DefaultAccountComponent::class.simpleName
    }

    private val scope = componentScope()

    private val _authData = MutableValue<Option<EmailAuthData>>(Option.None)
    override val authData: Value<Option<EmailAuthData>>
        get() = _authData

    private val _otpValue = MutableValue("")
    override val otpValue: Value<String> = _otpValue

    private val _otpStatus = MutableValue<OtpStatus>(OtpStatus.BeforeSendingOtp)
    override val otpStatus: Value<OtpStatus> = _otpStatus

    private val _accountCreateState =
        MutableValue<AccountCreateStatus>(CreatingAccount)
    override val accountCreateStatus: Value<AccountCreateStatus>
        get() = _accountCreateState

    init {
        if (otpStatus.value is OtpStatus.BeforeSendingOtp) {
            requestEmailAuth()
        }
    }

    override fun setOtp(value: String) {
        _otpValue.value = value
    }

    override fun requestEmailAuth() {
        scope.launch {
            _otpStatus.post(OtpStatus.SendingOtp)
            val result =
                authService.requestEmailAuth(userEmail.value, EmailAuthData.Purpose.CREATE_ACCOUNT)

            when (result) {
                is Result.Ok -> {
                    Log.d(TAG, "requestEmailAuth: ${result.value}")
                    _authData.post(Some(result.value))
                    _otpStatus.post(OtpStatus.OtpSent(result.value))
                }

                is Result.Err -> {
                    Log.d(TAG, "requestEmailAuth: ${result.err}")
                    _otpStatus.post(OtpStatus.OtpSentFailed(result.err.message.toString()))
                }
            }
        }
    }

    override fun verifyOtp() {
        val otpStatus = otpStatus.value
        if (
            otpStatus is OtpStatus.OtpSent &&
            otpValue.value.length == 6 &&
            authData.value.isSome()
        ) {
            val authData = authData.value.unwrap()
            _otpStatus.post(OtpStatus.VerifyingOtp(authData))

            scope.launch(Dispatchers.IO) {
                when (val result = authService.verifyEmailAuth(authData.authId, otpValue.value)) {
                    is Result.Ok -> {
                        Log.d(TAG, "verifyOtp: ${result.value}")
                        _authData.post(Some(result.value))

                        when (result.value.status) {
                            EmailAuthData.Status.VERIFIED -> {
                                _otpStatus.post(OtpStatus.OtpVerified(result.value))
                            }

                            EmailAuthData.Status.NEED_VERIFICATION -> {
                                _otpStatus.post(OtpStatus.OtpInvalid(result.value))
                            }

                            else -> {
                                _otpStatus.post(OtpStatus.NoAttemptsLeft(result.value))
                            }
                        }
                    }

                    is Result.Err -> {
                        Log.d(TAG, "verifyOtp: ${result.err}")

                        _otpStatus.post(
                            OtpStatus.OtpVerificationError(
                                authData,
                                "Something went wrong!"
                            )
                        )
                    }
                }
            }
        }
    }


    override fun createAccount() {
        authData.value.ifSome {
            if (it.status == EmailAuthData.Status.VERIFIED && !it.isExpired()) {
                val authData = authData.value.unwrap()
                scope.launch(Dispatchers.IO) {
                    _accountCreateState.post(CreatingAccount)

                    val result = authService.registerNewAccount(
                        verifiedAuthId = authData.authId,
                        credential = UserCredential(
                            email = userEmail.value,
                            password = userPassword.value
                        ),
                        userInfo = BasicInfo(
                            name = name.value,
                            gender = gender.value,
                            dateOfBirth = dob.value
                        ),
                        pictureData = pictureData
                    )

                    delay(3000)

                    when (result) {
                        is Result.Ok -> {
                            Log.d(TAG, "createAccount: ${result.value}")
                            _accountCreateState.post(AccountCreated)
                            delay(2000)
                            onAccountCreated(result.value)
                        }

                        is Result.Err -> {
                            Log.d(TAG, "createAccount: ${result.err}")
                            _accountCreateState.post(AccountCreateFailed(result.err.message.toString()))
                        }
                    }
                }
            } else if (it.isExpired()) {
                val expiredAuth = it.copy(status = EmailAuthData.Status.OTP_EXP)
                _otpStatus.post(OtpStatus.OtpExpired)
                _authData.post(Some(expiredAuth))
            }
        }
    }
}