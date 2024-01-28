package desidev.hango.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import desidev.hango.ui.screens.discover.DiscoverContent
import desidev.hango.ui.screens.following.FollowingContent

@Composable
fun HomeContent(component: HomeComponent) {
    val stack by component.childStack.subscribeAsState()

    Children(stack = stack) {
        when (val child = it.instance) {
            is HomeComponent.Child.DiscoverScreen -> DiscoverContent(child.component)
            is HomeComponent.Child.FollowingScreen -> FollowingContent(child.component)
        }
    }
}