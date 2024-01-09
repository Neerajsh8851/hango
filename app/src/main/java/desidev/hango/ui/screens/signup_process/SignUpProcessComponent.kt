package desidev.hango.ui.screens.signup_process

import android.net.Uri
import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.model.Gender
import desidev.hango.ui.screens.signup_process.auth.AuthComponent
import desidev.hango.ui.screens.signup_process.auth.DefaultAuthComponent
import desidev.hango.ui.screens.signup_process.profile.DefaultProfileComponent
import desidev.hango.ui.screens.signup_process.profile.ProfileComponent
import desidev.hango.ui.screens.signup_process.signup.DefaultSignUpComponent
import desidev.hango.ui.screens.signup_process.signup.SignUpComponent
import desidev.kotlin.utils.Option
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
) : ComponentContext by context, SignUpProcessComponent {

    private val navigation = StackNavigation<Config>()
    private val userEmail = MutableValue("neerajkaemail@meraemail.com")
    private val userPassword = MutableValue("9890808")

    private val name = MutableValue("")
    private val dob = MutableValue(LocalDate.now().minusYears(18))
    private val gender = MutableValue(Gender.Male)
    private val profilePic = MutableValue<Option<Uri>>(Option.None)

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
                    }
                )
            )

            Config.EmailAuth -> Child.Auth(
                DefaultAuthComponent(
                    context,
                    userEmail,
                    otpSendRequestHandle = { }
                )
            )
        }
    }
}