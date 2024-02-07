package desidev.hango.ui.screens.signup

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import desidev.hango.api.HangoAuthService
import desidev.hango.api.model.Gender
import desidev.hango.api.model.PictureData
import desidev.hango.ui.screens.photocrop.DefaultPhotoCropComponent
import desidev.hango.ui.screens.signup.SignupComponent.Config
import desidev.hango.ui.screens.signup.account.DefaultAccountComponent
import desidev.hango.ui.screens.signup.account.OnAccountCreated
import desidev.hango.ui.screens.signup.profile.DefaultProfileComponent
import desidev.hango.ui.screens.signup.signup.DefaultUserCredentialComponent
import desidev.kotlinutils.Option
import desidev.kotlinutils.runIfSome
import io.ktor.http.ContentType
import java.io.ByteArrayOutputStream
import java.time.LocalDate

class DefaultSignUpComponent(
    componentContext: ComponentContext,
    private val authService: HangoAuthService,
    private val onAccountCreated: OnAccountCreated
) : ComponentContext by componentContext,
    SignupComponent {

    companion object {
        val TAG = DefaultUserCredentialComponent::class.simpleName
    }

    private val navigation = StackNavigation<Config>()
    private val userEmail = MutableValue("neerajshdev@gmail.com")
    private val userPassword = MutableValue("123456")

    private val name = MutableValue("test account")
    private val dob = MutableValue(LocalDate.now().minusYears(18))
    private val gender = MutableValue(Gender.Male)
    private val profilePic = MutableValue<Option<ImageBitmap>>(Option.None)

    override val child = childStack(
        source = navigation,
        handleBackButton = true,
        initialConfiguration = Config.UserCredential
    ) { config, context ->
        when (config) {
            is Config.UserCredential -> SignupComponent.Child.SignUp(
                DefaultUserCredentialComponent(
                    context = context,
                    userEmail = userEmail,
                    userPassword = userPassword,
                    onEmailUpdate = { userEmail.value = it },
                    onPasswordUpdate = { userPassword.value = it },
                    onSubmitClick = { navigation.push(Config.Profile) }
                )
            )

            is Config.Profile -> SignupComponent.Child.Profile(
                DefaultProfileComponent(
                    context = context,
                    name = name,
                    dob = dob,
                    gender = gender,
                    profilePic = profilePic,
                    onNameEdit = { name.value = it },
                    onDobValueSelect = { dob.value = it },
                    onGenderValueSelect = { gender.value = it },
                    onProfilePicSelect = {
                        navigation.push(Config.PhotoCrop(it))
                    },
                    onSubmit = {
                        navigation.push(Config.Account)
                    }
                )
            )

            is Config.PhotoCrop -> SignupComponent.Child.PhotoCrop(
                component = DefaultPhotoCropComponent(
                    componentContext = context,
                    imageUri = config.imageUri,
                    onPhotoCrop = { bitmap ->
                        navigation.pop()
                        profilePic.value = Option.Some(bitmap)
                    }
                )
            )

            is Config.Account -> SignupComponent.Child.Account(
                DefaultAccountComponent(
                    context = context,
                    userEmail = userEmail,
                    userPassword = userPassword,
                    authService = authService,
                    name = name,
                    dob = dob,
                    gender = gender,
                    pictureData = profilePic.value.runIfSome {
                        val bitmap = it.asAndroidBitmap()
                        val array = ByteArrayOutputStream().use { stream ->
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                            stream.toByteArray()
                        }

                        PictureData(
                            array,
                            "${name.value}.jpg",
                            type = ContentType.Image.JPEG,
                        ).let { data ->
                            Option.Some(data)
                        }
                    },
                    onAccountCreated = onAccountCreated,
                )
            )

        }
    }
}