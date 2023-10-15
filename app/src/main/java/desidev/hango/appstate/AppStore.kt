package desidev.hango.appstate

import desidev.hango.states.Store

class AppStore {
    companion object {
        val store = Store<StateKeys>()

        fun configStore() {
            store.config {
                put(StateKeys.VerifyEmailState) {
                    SignUpScreenState.EnterEmail(
                        email = "",
                        isValid = false
                    )
                }
            }
        }
    }

    enum class StateKeys {
        VerifyEmailState
    }

}
