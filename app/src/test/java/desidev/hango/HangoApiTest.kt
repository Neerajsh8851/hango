package desidev.hango

import desidev.hango.api.impl.DefaultHangoApi
import desidev.hango.api.model.EmailAuthData
import desidev.kotlin.utils.Result
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HangoApiTest {
    val api = DefaultHangoApi("http://139.59.85.69")

    @Test
    fun createEmailAuth_Test(): Unit = runBlocking {
        val result = api.createEmailAuth("neerajshdev@gmail.com", EmailAuthData.Purpose.CREATE_ACCOUNT)
        when(result) {
            is Result.Err -> println(result.err)
            is Result.Ok -> println(result.value)
        }
    }

    @Test
    fun verifyEmailAuth_Test(): Unit = runBlocking {
        // createEmailAuth test ka result
        val authId = "659d140eb3f1b7765f500835"

        // otp value jo createEmailAuth request me diye hue email address pe aya hoga
        val otpValue = "699185"

        when(val result = api.verifyEmailAuth(otpValue, authId)) {
            is Result.Err -> println(result.err)
            is Result.Ok -> println(result.value)
        }
    }
}