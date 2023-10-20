package desidev.hango.appstate

import desidev.hango.states.IAction
import desidev.hango.states.StateValue

data class SignInScreenState(
    val emailAddress: String,
    val password: String,
    val hidePassword: Boolean
) {
    companion object {
        fun new ()  = SignInScreenState("", "", true)
    }
}



fun StateValue<SignInScreenState>.updateEmail(email: String) {
    dispatch(IAction { prev -> prev.copy(emailAddress = email) })
}


fun StateValue<SignInScreenState>.updatePassword(password: String) {
    dispatch(IAction { prev ->
        prev.copy(password = password)
    })
}

fun StateValue<SignInScreenState>.togglePasswordVisibility() = dispatch(IAction { prev ->
    prev.copy(hidePassword = prev.hidePassword.not())
})


