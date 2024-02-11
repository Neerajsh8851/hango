package desidev.hango.api

sealed interface LoginError {

    /**
     * LoginError representing an invalid credential.
     * This error occurs when the user provides an invalid username or password.
     */
    data object InvalidCredential : LoginError

    /**
     * This error occurs when the login process failed due to an exception.
     * @param exception The exception that caused the error.
     */
    data class Error(val exception: Exception) : LoginError
}