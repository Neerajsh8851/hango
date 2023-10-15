package desidev.hango.appstate

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import desidev.hango.states.IAction
import desidev.hango.states.IAsyncSideEffect
import desidev.hango.states.Observer
import desidev.hango.states.StateValue
import desidev.hango.states.ValueDispatch
import kotlinx.coroutines.delay

sealed class SignUpScreenState {
    data class EnterEmail(val email: String, val isValid: Boolean) : SignUpScreenState()
    data class EnterOtp(val email: String, val otp: String) : SignUpScreenState()
    data class SetPassword(val email: String, val password: String) : SignUpScreenState()


    data class UpdateEmail(val email: String) : IAction<SignUpScreenState> {
        override fun execute(value: SignUpScreenState): SignUpScreenState {
            return if (value is SignUpScreenState.EnterEmail) {
                SignUpScreenState.EnterEmail(email = this.email, isValid = false)
            } else {
                value
            }
        }
    }

    data object VerifyEmail : IAsyncSideEffect<SignUpScreenState> {
        override suspend fun execute(
            state: SignUpScreenState,
            dispatch: ValueDispatch<SignUpScreenState>
        ) {
            if (state is SignUpScreenState.EnterEmail) {
                if (state.isValid) {
                    delay(1000)
                    dispatch.dispatch(
                        IAction {
                            SignUpScreenState.EnterOtp(state.email, "")
                        }
                    )
                } else {
                    Log.d(VerifyEmail::class.java.name, "execute: email is not valid")
                }
            }
        }
    }
}



