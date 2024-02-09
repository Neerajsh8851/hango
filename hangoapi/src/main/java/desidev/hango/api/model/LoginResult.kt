package desidev.hango.api.model

data class LoginResult(
    val status: Status,
    val data: SessionInfo?
) {
    enum class Status {
        VALID, INVALID
    }
}
