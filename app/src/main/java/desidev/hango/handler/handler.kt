package desidev.hango.handler

fun interface EmailUpdateHandle {
    fun updateEmail(value: String)
}

fun interface PasswordUpdateHandle {
    fun updatePassword(value: String)
}

