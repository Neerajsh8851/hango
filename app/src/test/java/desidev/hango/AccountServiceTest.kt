package desidev.hango

import desidev.hango.api.DefaultAuthService
import desidev.hango.api.model.EmailAuthData
import desidev.kotlin.utils.Result
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AccountServiceTest {
    private val authService = DefaultAuthService("http://139.59.85.69")

    @Test
    fun createEmailAuth_Test(): Unit = runBlocking {
        val result = authService.requestEmailAuth(
                "neerajshdev@gmail.com",
                EmailAuthData.Purpose.CREATE_ACCOUNT
            )
        when (result) {
            is Result.Err -> println(result.err)
            is Result.Ok -> println(result.value)
        }
    }

    @Test
    fun verifyEmailAuth_Test(): Unit = runBlocking {
        // createEmailAuth test ka result
        val authId = "65a423e14058d41c57ee2805"

        // otp value jo createEmailAuth request me diye hue email address pe aya hoga
        val otpValue = "951241"

        when (val result = authService.verifyEmailAuth(authId = authId, otpValue = otpValue)) {
            is Result.Err -> println(result.err)
            is Result.Ok -> println(result.value)
        }
    }
}