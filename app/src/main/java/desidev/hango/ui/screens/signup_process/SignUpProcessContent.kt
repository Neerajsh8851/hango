package desidev.hango.ui.screens.signup_process

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import desidev.hango.ui.screens.signup_process.auth.AuthContent
import desidev.hango.ui.screens.signup_process.profile.ProfileContent
import desidev.hango.ui.screens.signup_process.signup.SignUpContent

@Composable
fun SignUpProcessContent(component: SignUpProcessComponent) {
    Children(stack = component.child, animation = stackAnimation(fade() + scale())) {
        when (val child = it.instance) {
            is Child.SignUp -> SignUpContent(component = child.component)
            is Child.Profile -> ProfileContent(component = child.component)
            is Child.Auth -> AuthContent(component = child.component)
        }
    }
}