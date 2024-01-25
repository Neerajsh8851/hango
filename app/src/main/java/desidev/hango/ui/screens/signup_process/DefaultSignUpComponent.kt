package desidev.hango.ui.screens.signup_process

import android.net.Uri
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import desidev.hango.api.HangoAuthService
import desidev.hango.api.model.Gender
import desidev.hango.ui.screens.signup_process.account.DefaultAccountComponent
import desidev.hango.ui.screens.signup_process.profile.DefaultProfileComponent
import desidev.hango.ui.screens.signup_process.signup.DefaultSignUpComponent
import desidev.kotlinutils.Option
import java.time.LocalDate

class DefaultSignUpComponent(
    context: ComponentContext,
    private val authService: HangoAuthService,
    private val onAccountCreated: DefaultAccountComponent.OnAccountCreated
) : ComponentContext by context,
    SignUpComponent {

    companion object {
        val TAG = DefaultSignUpComponent::class.simpleName
    }

    private val navigation = StackNavigation<SignUpComponent.Config>()
    private val userEmail = MutableValue("neerajshdev@gmail.com")
    private val userPassword = MutableValue("123456")

    private val name = MutableValue("test account")
    private val dob = MutableValue(LocalDate.now().minusYears(18))
    private val gender = MutableValue(Gender.Male)
    private val profilePic = MutableValue<Option<Uri>>(Option.None)

    override val child = childStack(
        source = navigation,
        handleBackButton = true,
        initialConfiguration = SignUpComponent.Config.SignUp
    ) { config, context ->
        when (config) {
            is SignUpComponent.Config.SignUp -> SignUpComponent.Child.SignUp(
                DefaultSignUpComponent(
                    context = context,
                    userEmail = userEmail,
                    userPassword = userPassword,
                    onEmailUpdate = { userEmail.value = it },
                    onPasswordUpdate = { userPassword.value = it },
                    onSubmitClick = { navigation.push(SignUpComponent.Config.Profile) }
                )
            )

            is SignUpComponent.Config.Profile -> SignUpComponent.Child.Profile(
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
                    onSubmitClick = { navigation.push(SignUpComponent.Config.Account) }
                )
            )

            is SignUpComponent.Config.Account -> SignUpComponent.Child.Account(
                DefaultAccountComponent(
                    context = context,
                    userEmail = userEmail,
                    userPassword = userPassword,
                    authService = authService,
                    name = name,
                    dob = dob,
                    gender = gender,
                    onAccountCreated = onAccountCreated,
                )
            )
        }
    }
}