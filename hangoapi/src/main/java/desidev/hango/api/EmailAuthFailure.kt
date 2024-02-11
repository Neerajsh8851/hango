package desidev.hango.api

sealed interface EmailAuthFailure {
    /**
     * The email address might not valid or the email was already used in another account.
     */
    data class EmailAuthCreationFailed(val responseStatus: String, val message: String) :
        EmailAuthFailure

    /**
     * An error occurred while creating the email authentication data.
     * @param exception The exception that occurred.
     *
     * this is usually a network error / timeout
     */
    data class EmailAuthErr(var exception: Exception) : EmailAuthFailure
}
