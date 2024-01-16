package desidev.hango.ui.screens.signup_process.account

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.api.HangoAuthService
import desidev.hango.api.model.BasicInfo
import desidev.hango.api.model.EmailAuthData
import desidev.hango.api.model.Gender
import desidev.hango.api.model.SessionInfo
import desidev.hango.api.model.UserCredential
import desidev.hango.ui.componentScope
import desidev.hango.ui.post
import desidev.hango.ui.screens.signup_process.account.AccountComponent.*
import desidev.kotlin.utils.Option
import desidev.kotlin.utils.Option.Some
import desidev.kotlin.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

class DefaultAccountComponent(
    context: ComponentContext,
    private val authService: HangoAuthService,
    override val userEmail: Value<String>,
    private val userPassword: Value<String>,
    private val name: Value<String>,
    private val gender: Value<Gender>,
    private val dob: Value<LocalDate>,
    private val onAccountCreated: OnAccountCreated
) : ComponentContext by context, AccountComponent {
    fun interface OnAccountCreated {
        operator fun invoke(sessionInfo: SessionInfo)
    }

    companion object {
        val TAG = DefaultAccountComponent::class.simpleName
    }

    private val scope = componentScope()

    private val _authData = MutableValue<Option<EmailAuthData>>(Option.None)
    override val authData: Value<Option<EmailAuthData>>
        get() = _authData

    private val _otpValue = MutableValue("")
    override val otpValue: Value<String> = _otpValue

    private val _otpStatus = MutableValue<OtpStatus>(OtpStatus.SendingOtp)
    override val otpStatus: Value<OtpStatus> = _otpStatus

    private val _accountCreateState =
        MutableValue<AccountCreateStatus>(AccountCreateStatus.CreatingAccount)
    override val accountCreateStatus: Value<AccountCreateStatus>
        get() = _accountCreateState

    override fun setOtp(value: String) {
        _otpValue.value = value
    }

    override fun requestEmailAuth() {
        scope.launch {
            _otpStatus.value = OtpStatus.SendingOtp

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

    override fun verifyOtp(authData: EmailAuthData) {
        val otpStatus = otpStatus.value
        if (
            otpStatus is OtpStatus.OtpSent &&
            otpStatus.authData == authData &&
            otpValue.value.length == 6
        ) {
            _otpStatus.post(OtpStatus.VerifyingOtp(authData))

            scope.launch {
                when (val result = authService.verifyEmailAuth(authData.authId, otpValue.value)) {
                    is Result.Ok -> {
                        when (result.value.status) {
                            EmailAuthData.Status.VERIFIED -> {
                                _otpStatus.post(OtpStatus.OtpVerified(result.value))
                            }

                            EmailAuthData.Status.NO_ATTEMPT_LEFT -> {
                                _otpStatus.post(OtpStatus.NoAttemptsLeft(result.value))
                            }

                            else -> {
                                _otpStatus.post(OtpStatus.OtpInvalid(result.value))
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


    override fun createAccount(authData: EmailAuthData) {
        if (
            authData.status == EmailAuthData.Status.VERIFIED &&
            !authData.isExpired()
        ) {
            scope.launch(Dispatchers.IO) {
                _accountCreateState.value = AccountCreateStatus.CreatingAccount

                val result = authService.registerNewAccount(
                    verifiedAuthId = authData.authId,
                    credential = UserCredential(
                        userEmail.value,
                        userPassword.value
                    ),
                    userInfo = BasicInfo(
                        name = name.value,
                        gender = gender.value,
                        dateOfBirth = dob.value
                    ),
                    pictureData = Option.None
                )

                when (result) {

                    is Result.Ok -> {
                        Log.d(TAG, "createAccount: ${result.value}")
                        _accountCreateState.value = AccountCreateStatus.AccountCreated

                        delay(2000)
                        onAccountCreated(result.value)
                    }

                    is Result.Err -> {
                        Log.d(TAG, "createAccount: ${result.err}")
                        _accountCreateState.value =
                            AccountCreateStatus.AccountCreateFailed(result.err.message.toString())
                    }
                }
            }

        } else if (authData.isExpired()) {
            val expiredAuth = authData.copy(status = EmailAuthData.Status.OTP_EXP)
            _otpStatus.post(OtpStatus.OtpExpired(authData))
            _authData.post(Some(expiredAuth))
        }
    }
}