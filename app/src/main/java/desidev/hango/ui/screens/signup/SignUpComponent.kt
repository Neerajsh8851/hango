package desidev.hango.ui.screens.signup

import android.net.Uri
import android.os.Parcelable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.screens.photocrop.PhotoCropComponent
import desidev.hango.ui.screens.signup.account.AccountComponent
import desidev.hango.ui.screens.signup.profile.ProfileComponent
import desidev.hango.ui.screens.signup.signup.UserCredentialComponent
import kotlinx.parcelize.Parcelize

/**
 * SignUpComponent Interface
 *
 * The SignUpComponent is the root interface for the sign-up process, divided into three stages:
 * Credential Component -> Profile Component -> Account Component.
 *
 * Each component serves a specific purpose in guiding the user through the sign-up journey.
 *
 * ### Components Overview:
 *
 * - **Credential Component:**
 *   - Collects and validates user email and password.
 *   - Essential for the initial stages of account creation.
 *
 * - **Profile Component:**
 *   - Gathers additional user information (name, age, gender).
 *   - Optional profile picture inclusion to personalize the account.
 *
 * - **Account Component:**
 *   - Verifies provided information.
 *   - Registers the user's account securely in the system.
 *
 * ### Child Property:
 *
 * The `child` property represents the value holding the ChildStack configuration for managing child components within the SignUpComponent. It encapsulates the configuration and child components necessary for guiding the user through each sign-up stage.
 */
interface SignUpComponent {
    val child: Value<ChildStack<Config, Child>>
    sealed interface Config : Parcelable {
        @Parcelize
        data object UserCredential : Config

        @Parcelize
        data object Profile : Config

        @Parcelize
        data class PhotoCrop(val imageUri: Uri): Config

        @Parcelize
        data object Account : Config
    }

    sealed interface Child {
        data class SignUp(val component: UserCredentialComponent) : Child
        data class Profile(val component: ProfileComponent) : Child
        data class Account(val component: AccountComponent) : Child
        data class PhotoCrop(val component: PhotoCropComponent) : Child
    }
}


