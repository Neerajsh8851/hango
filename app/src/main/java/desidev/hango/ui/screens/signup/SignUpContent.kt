package desidev.hango.ui.screens.signup

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import desidev.hango.ui.screens.photocrop.PhotoCropContent
import desidev.hango.ui.screens.signup.SignupComponent.Child.*
import desidev.hango.ui.screens.account.AccountContent
import desidev.hango.ui.screens.profile.ProfileContent
import desidev.hango.ui.screens.usercredential.UserCredentialContent

@Composable
fun SignUpContent(component: SignupComponent) {
    Children(stack = component.child, animation = stackAnimation(fade() + scale())) {
        when (val child = it.instance) {
            is SignUpScreen -> UserCredentialContent(component = child.component)
            is ProfileScreen -> ProfileContent(component = child.component)
            is AccountScreen -> AccountContent(component = child.component)
            is PhotoCropScreen -> PhotoCropContent(component = child.component)
        }
    }
}