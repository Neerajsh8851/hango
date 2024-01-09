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
import desidev.hango.api.HangoAuthApi
import desidev.hango.api.model.EmailAuthData
import desidev.hango.api.model.UserCredential
import desidev.hango.compmodel.DefaultAuthCompModel
import desidev.hango.model.Gender
import desidev.hango.ui.componentScope
import desidev.hango.ui.screens.signup_process.auth.AuthComponent
import desidev.hango.ui.screens.signup_process.auth.DefaultAuthComponent
import desidev.hango.ui.screens.signup_process.profile.DefaultProfileComponent
import desidev.hango.ui.screens.signup_process.profile.ProfileComponent
import desidev.hango.ui.screens.signup_process.signup.DefaultSignUpComponent
import desidev.hango.ui.screens.signup_process.signup.SignUpComponent
import desidev.kotlin.utils.Option
import desidev.kotlin.utils.Result
import desidev.kotlin.utils.ifNone
import desidev.kotlin.utils.ifSome
import desidev.kotlin.utils.runIfSome
import desidev.kotlin.utils.isSome
import desidev.kotlin.utils.unwrap
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.handleCoroutineException
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


class DefaultSignUpProcess(
    context: ComponentContext,
    authCompModel: DefaultAuthCompModel,
) : ComponentContext by context, SignUpProcessComponent {

    companion object {
        val TAG = DefaultAuthComponent::class.simpleName
    }

    private val navigation = StackNavigation<Config>()
    private val userEmail = MutableValue("neerajshdev@gmail.com")
    private val userPassword = MutableValue("123456")

    private val name = MutableValue("test account")
    private val dob = MutableValue(LocalDate.now().minusYears(18))
    private val gender = MutableValue(Gender.Male)
    private val profilePic = MutableValue<Option<Uri>>(Option.None)

    private var emailAuthData: Option<EmailAuthData> = Option.None

    private val scope = componentScope()

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
                    onSubmitClick = {
                        navigation.push(Config.Profile)
                    }
                )
            )

            Config.Profile -> Child.Profile(
                DefaultProfileComponent(
                    context = context,
                    name = name,
                    dob = dob,
                    gender = gender,
                    profilePic = profilePic,
                    nameCallback = {
                        name.value = it
                    },
                    dobCallback = { dob.value = it },
                    genderCallback = { gender.value = it },
                    profileUrlCallback = { uri: Uri ->
                        profilePic.value = Option.Some(uri)
                    },
                    onSubmitClick = {
                        navigation.push(Config.EmailAuth)

                        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                            throwable.printStackTrace()
                        }

                        scope.launch(exceptionHandler) {
                            val result = authCompModel.requestEmailAuth(
                                userEmail.value,
                                EmailAuthData.Purpose.CREATE_ACCOUNT
                            )
                            emailAuthData = when (result) {
                                is Result.Ok -> Option.Some(result.value)
                                is Result.Err -> Option.None
                            }
                        }
                    }
                )
            )

            Config.EmailAuth -> Child.Auth(
                DefaultAuthComponent(
                    context = context,
                    userEmail = userEmail,
                    onOtpEnter = { otp ->
                        scope.launch {
                            emailAuthData = emailAuthData.runIfSome {
                                when (val result = authCompModel.verifyEmailAuth(it.id, otp)) {
                                    is Result.Err -> {
                                        Log.d(TAG, "verify email result: ${result.err} ")
                                        Option.None
                                    }

                                    is Result.Ok -> {
                                        Log.d(TAG, "verify email result: ${result.value} ")
                                        Option.Some(result.value)
                                    }
                                }
                            }

                            emailAuthData.ifSome { auth ->
                                if (auth.status == EmailAuthData.Status.VERIFIED) {
                                    authCompModel.createAccount(
                                        verifiedAuthId = auth.id,
                                        credential = UserCredential(
                                            userEmail.value,
                                            userPassword.value
                                        ),
                                        userInfo = HangoAuthApi.BasicUserInfo(
                                            firstname = name.value,
                                            lastname = name.value,
                                            gender = gender.value
                                        ),
                                        pictureData = Option.None
                                    )
                                }
                            }
                        }
                    },
                    onSendAgain = {}
                )
            )
        }
    }
}