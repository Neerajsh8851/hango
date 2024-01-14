package desidev.hango.ui.screens.signup_process

import android.net.Uri
import android.os.Parcelable
import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.api.HangoAuthService
import desidev.hango.api.model.BasicInfo
import desidev.hango.api.model.EmailAuthData
import desidev.hango.api.model.Gender
import desidev.hango.api.model.UserCredential
import desidev.hango.ui.componentScope
import desidev.hango.ui.screens.signup_process.auth.AuthComponent
import desidev.hango.ui.screens.signup_process.auth.DefaultAuthComponent
import desidev.hango.ui.screens.signup_process.profile.DefaultProfileComponent
import desidev.hango.ui.screens.signup_process.profile.ProfileComponent
import desidev.hango.ui.screens.signup_process.signup.DefaultSignUpComponent
import desidev.hango.ui.screens.signup_process.signup.SignUpComponent
import desidev.kotlin.utils.Option
import desidev.kotlin.utils.Result
import desidev.kotlin.utils.asSome
import desidev.kotlin.utils.ifSome
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

/**
 * Root component for the sign up process
 */
interface SignUpProcessComponent {
    val child: Value<ChildStack<Config, Child>>
}

sealed interface Config : Parcelable {
    @Parcelize
    data object SignUp : Config

    @Parcelize
    data object Profile : Config

    @Parcelize
    data object EmailAuth : Config
}

sealed interface Child {
    data class SignUp(val component: SignUpComponent) : Child
    data class Profile(val component: ProfileComponent) : Child
    data class Auth(val component: AuthComponent) : Child
}


class DefaultSignUpProcessComponent(
    context: ComponentContext,
    private val authService: HangoAuthService,
) : ComponentContext by context, SignUpProcessComponent {

    companion object {
        val TAG = DefaultSignUpProcessComponent::class.simpleName
    }

    private val navigation = StackNavigation<Config>()
    private val userEmail = MutableValue("neerajshdev@gmail.com")
    private val userPassword = MutableValue("123456")

    private val name = MutableValue("test account")
    private val dob = MutableValue(LocalDate.now().minusYears(18))
    private val gender = MutableValue(Gender.Male)
    private val profilePic = MutableValue<Option<Uri>>(Option.None)

    private var emailAuthData: MutableValue<Option<EmailAuthData>> = MutableValue(Option.None)

    private val scope = componentScope()


    private fun requestEmailAuth() {
        scope.launch {
            val result = authService.requestEmailAuth(
                userEmail.value,
                EmailAuthData.Purpose.CREATE_ACCOUNT
            )

            when (result) {
                is Result.Ok -> {
                    Log.d(TAG, "requestEmailAuth: ${result.value}")
                    emailAuthData.value = Option.Some(result.value)
                }

                is Result.Err -> {
                    Log.d(TAG, "requestEmailAuth: ${result.err}")
                }
            }
        }
    }

    private fun verifyEmailAuth(otp: String) {
        scope.launch {
            emailAuthData.value.ifSome {
                when (val result = authService.verifyEmailAuth(it.authId, otp)) {
                    is Result.Ok -> {
                        Log.d(TAG, "verifyEmailAuth: ${result.value}")
                    }

                    is Result.Err -> {
                        Log.d(TAG, "verifyEmailAuth: ${result.err}")
                    }
                }
            }
        }
    }

    private fun createAccount() {
        scope.launch {
            val result = authService.registerNewAccount(
                verifiedAuthId = emailAuthData.value.asSome().value.authId,
                credential = UserCredential(
                    userEmail.value,
                    userPassword.value
                ),
                userInfo = BasicInfo(
                    name = name.value,
                    gender = gender.value,
                    dateOfBirth = dob.value
                ),
                pictureData = Option.None
            )

            when (result) {
                is Result.Ok -> {
                    Log.d(TAG, "createAccount: ${result.value}")
                }

                is Result.Err -> {
                    Log.d(TAG, "createAccount: ${result.err}")
                }
            }
        }
    }

    override val child = childStack(
        source = navigation,
        handleBackButton = true,
        initialConfiguration = Config.SignUp
    ) { config, context ->
        when (config) {
            is Config.SignUp -> Child.SignUp(
                DefaultSignUpComponent(
                    context = context,
                    userEmail = userEmail,
                    userPassword = userPassword,
                    onEmailUpdate = { userEmail.value = it },
                    onPasswordUpdate = { userPassword.value = it },
                    onSubmitClick = { navigation.push(Config.Profile) }
                )
            )

            is Config.Profile -> Child.Profile(
                DefaultProfileComponent(
                    context = context,
                    name = name,
                    dob = dob,
                    gender = gender,
                    profilePic = profilePic,
                    nameCallback = { name.value = it },
                    dobCallback = { dob.value = it },
                    genderCallback = { gender.value = it },
                    profileUrlCallback = { uri: Uri -> profilePic.value = Option.Some(uri) },
                    onSubmitClick = { navigation.push(Config.EmailAuth) }
                )
            )

            is Config.EmailAuth -> Child.Auth(
                DefaultAuthComponent(
                    context = context,
                    userEmail = userEmail,
                    authData = emailAuthData,
                    onOtpEnter = { verifyEmailAuth(it) },
                    onRequestEmailAuth = { requestEmailAuth() }
                )
            )
        }
    }
}