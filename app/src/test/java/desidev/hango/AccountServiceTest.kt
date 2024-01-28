package desidev.hango

import desidev.hango.api.DefaultAuthService
import desidev.hango.api.model.BasicInfo
import desidev.hango.api.model.EmailAuthData
import desidev.hango.api.model.Gender
import desidev.hango.api.model.PictureData
import desidev.hango.api.model.UserCredential
import desidev.kotlinutils.Option
import desidev.kotlinutils.Result
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.File
import java.time.LocalDate

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
            is Result.Ok -> {
                println(result.value)
                println("is expired = ${result.value.isExpired()}")
            }

        }
    }

    @Test
    fun verifyEmailAuth_Test(): Unit = runBlocking {
        // createEmailAuth test ka result
        val authId = "65b494d336079c1f25468eaf"

        // otp value jo createEmailAuth request me diye hue email address pe aya hoga
        val otpValue = "009978"

        when (val result = authService.verifyEmailAuth(authId = authId, otpValue = otpValue)) {
            is Result.Err -> println(result.err)
            is Result.Ok -> println(result.value)
        }
    }

    @Test
    fun `create account test`(): Unit = runBlocking {
        val email = "neerajshdev@gmail.com"
        val password = "123456"
        val verifiedAuthId = "65b494d336079c1f25468eaf"

        val pathToProfilePic = "C:\\Users\\nsxyl\\OneDrive\\Pictures\\profile.png"
        val imageData = File(pathToProfilePic).inputStream().use { it.readAllBytes() }

        val pictureData = PictureData(imageData, "profile.png", PictureData.Mimetype("Image", "png"))

        val authService = DefaultAuthService("http://139.59.85.69")

        val result = authService.registerNewAccount(
            verifiedAuthId = verifiedAuthId,
            credential = UserCredential(email, password),
            pictureData = Option.Some(pictureData),
            userInfo = BasicInfo(
                name = "test-account",
                dateOfBirth = LocalDate.now(),
                gender = Gender.Other
            )
        )

        when(result) {
            is Result.Err -> println(result.err)
            is Result.Ok -> println(result.value)
        }
    }
}