package desidev.hango.ui.screens.signup_process.signup

import com.arkivanov.decompose.value.Value

interface SignUpComponent  {
    val userEmail: Value<String>
    val userPassword: Value<String>
    val hidePassword: Value<Boolean>
    val confirmPassword: Value<String>

    fun setEmail(email: String)
    fun setPassword(password: String)
    fun setConfirmPassword(password: String)
    fun togglePasswordVisibility()

    fun onSubmit()
}

