package desidev.hango.compmodel

import com.arkivanov.decompose.value.Value
import desidev.hango.model.User
import desidev.hango.api.model.UserCredential
import desidev.hango.handler.SignInRequestHandle

interface AuthCompModel: SignInRequestHandle {
    val user : Value<User>
    fun getCurrentUserToken(): String
    fun signOut()
}


class DefaultAuthCompModel : AuthCompModel {
    override val user: Value<User>
        get() = TODO("Not yet implemented")

    override fun getCurrentUserToken(): String {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun requestSignIn(credential: UserCredential) {
    }
}