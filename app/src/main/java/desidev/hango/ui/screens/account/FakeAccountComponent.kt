package desidev.hango.ui.screens.account

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.EmailAuthData
import desidev.kotlinutils.Option

class FakeAccountComponent : AccountComponent {
    override val userEmail: Value<String> = MutableValue("myname@example.com")
    private val _otpValue = MutableValue("45648")
    override val otpValue: Value<String> = _otpValue

    override val authData: Value<Option<EmailAuthData>>
        get() = MutableValue(Option.None)

    override val otpStatus: Value<AccountComponent.OtpStatus>
        get() = MutableValue(AccountComponent.OtpStatus.SendingOtp)

    override val accountCreateStatus: Value<AccountComponent.AccountCreateStatus>
        get() = MutableValue(AccountComponent.AccountCreateStatus.CreatingAccount)

    override fun requestEmailAuth() {
    }

    override fun setOtp(value: String) {
        _otpValue.value = value
    }

    override fun verifyOtp() {
    }

    override fun createAccount() {
    }

    override fun goBack() {}
}