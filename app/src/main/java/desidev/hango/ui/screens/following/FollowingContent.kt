package desidev.hango.ui.screens.following

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import desidev.hango.ui.composables.UserItemsGrid

@Preview(apiLevel = 33)
@Composable
fun FollowingContentPreview() {
    FollowingContent(component = remember {
        FakeFollowingComponent()
    })
}


@Composable
fun FollowingContent(component: FollowingComponent) {
    val users by component.users.subscribeAsState()
    UserItemsGrid(users = users)
}