package desidev.hango.handler

import desidev.hango.api.model.UserCredential

fun interface EmailUpdateHandle {
    fun updateEmail(value: String)
}

fun interface PasswordUpdateHandle {
    fun updatePassword(value: String)
}

fun interface SignInRequestHandle {
    fun requestSignIn(credential: UserCredential)
}