package desidev.hango.ui.screens.usercredential

import com.arkivanov.decompose.value.Value

interface UserCredentialComponent  {
    val userEmail: Value<String>
    val userPassword: Value<String>
    val hidePassword: Value<Boolean>
    val confirmPassword: Value<String>

    fun setEmail(email: String)
    fun setPassword(password: String)
    fun setConfirmPassword(password: String)
    fun togglePasswordVisibility()

    fun onSubmit()
    fun goBack()
}

