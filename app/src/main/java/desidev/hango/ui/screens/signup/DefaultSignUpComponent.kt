package desidev.hango.ui.screens.signup

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.essenty.backhandler.BackDispatcher
import desidev.hango.api.AuthService
import desidev.hango.api.model.Gender
import desidev.hango.api.model.PictureData
import desidev.hango.ui.screens.account.DefaultAccountComponent
import desidev.hango.ui.screens.account.OnAccountCreated
import desidev.hango.ui.screens.account.OnGoBack
import desidev.hango.ui.screens.photocrop.DefaultPhotoCropComponent
import desidev.hango.ui.screens.profile.DefaultProfileComponent
import desidev.hango.ui.screens.signup.SignupComponent.Child
import desidev.hango.ui.screens.usercredential.DefaultUserCredentialComponent
import desidev.kotlinutils.Option
import desidev.kotlinutils.runIfSome
import io.ktor.http.ContentType
import kotlinx.parcelize.Parcelize
import java.io.ByteArrayOutputStream
import java.time.LocalDate

class DefaultSignUpComponent(
    componentContext: ComponentContext,
    private val authService: AuthService,
    private val onAccountCreated: OnAccountCreated,
    private val onGoBack: OnGoBack
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

    @OptIn(ExperimentalDecomposeApi::class)
    override val child = childStack(
        source = navigation,
        handleBackButton = true,
        initialConfiguration = Config.UserCredential
    ) { config, context ->
        when (config) {
            is Config.UserCredential -> Child.SignUpScreen(
                DefaultUserCredentialComponent(
                    context = context,
                    userEmail = userEmail,
                    userPassword = userPassword,
                    onEmailUpdate = { userEmail.value = it },
                    onPasswordUpdate = { userPassword.value = it },
                    onSubmitClick = { navigation.push(Config.Profile) },
                    onGoBack = {
                        navigation.pop { popped ->
                            if (!popped) onGoBack()
                        }
                    }
                )
            )

            is Config.Profile -> Child.ProfileScreen(
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
                        navigation.pushNew(Config.PhotoCrop(it))
                    },
                    onSubmit = {
                        navigation.pushNew(Config.Account)
                    }
                )
            )

            is Config.PhotoCrop -> Child.PhotoCropScreen(
                component = DefaultPhotoCropComponent(
                    componentContext = context,
                    imageUri = config.imageUri,
                    onPhotoCrop = { bitmap ->
                        navigation.pop()
                        profilePic.value = Option.Some(bitmap)
                    }
                )
            )

            is Config.Account -> Child.AccountScreen(
                DefaultAccountComponent(
                    context = context,
                    userEmail = userEmail,
                    userPassword = userPassword,
                    authService = authService,
                    name = name,
                    dob = dob,
                    gender = gender,
                    onAccountCreated = onAccountCreated,
                    onGoBack = { navigation.pop() },
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
                    }
                )
            )
        }
    }

    sealed interface Config : Parcelable {
        @Parcelize
        data object UserCredential : Config

        @Parcelize
        data object Profile : Config

        @Parcelize
        data object Account : Config

        @Parcelize
        data class PhotoCrop(val imageUri: Uri) : Config
    }
}