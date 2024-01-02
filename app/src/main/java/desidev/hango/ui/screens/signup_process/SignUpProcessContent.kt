package desidev.hango.ui.screens.signup_process.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import desidev.hango.ui.screens.signup_process.Child
import desidev.hango.ui.screens.signup_process.SignUpProcessComponent
import desidev.hango.ui.screens.signup_process.auth.AuthContent
import desidev.hango.ui.screens.signup_process.profile.ProfileContent

@Composable
fun SignUpProcessContent(component: SignUpProcessComponent, modifier: Modifier = Modifier) {
    Children(stack = component.child, modifier = modifier) {
        when (val child = it.instance) {
            is Child.SignUp -> SignUpContent(component = child.component)
            is Child.Profile -> ProfileContent(component = child.component)
            is Child.Auth -> AuthContent(component = child.component)
        }
    }
}