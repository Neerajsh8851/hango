package desidev.hango.compmodel

import com.arkivanov.decompose.value.Value
import desidev.hango.model.Profile
import desidev.hango.api.model.UserCredential
import desidev.hango.handler.SignInRequestHandle

interface AuthCompModel: SignInRequestHandle {
    val user : Value<Profile>
    fun getCurrentUserToken(): String
    fun signOut()
}


class DefaultAuthCompModel : AuthCompModel {
    override val user: Value<Profile>
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