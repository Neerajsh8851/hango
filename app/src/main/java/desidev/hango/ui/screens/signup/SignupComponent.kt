package desidev.hango.ui.screens.signup

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.screens.photocrop.PhotoCropComponent
import desidev.hango.ui.screens.account.AccountComponent
import desidev.hango.ui.screens.profile.ProfileComponent
import desidev.hango.ui.screens.usercredential.UserCredentialComponent

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
interface SignupComponent {
    val child: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class SignUpScreen(val component: UserCredentialComponent) : Child
        data class ProfileScreen(val component: ProfileComponent) : Child
        data class AccountScreen(val component: AccountComponent) : Child
        data class PhotoCropScreen(val component: PhotoCropComponent) : Child
    }
}


