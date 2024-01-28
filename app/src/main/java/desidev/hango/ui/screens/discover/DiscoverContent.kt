package desidev.hango.ui.screens.discover

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import desidev.hango.ui.composables.UserItemsGrid

@Preview(apiLevel = 33)
@Composable
fun DiscoverContentPreview() {
    DiscoverContent(component = remember {
        FakeDiscoverComponent()
    })
}

@Composable
fun DiscoverContent(component: DiscoverComponent) {
    val users by component.users.subscribeAsState()
    UserItemsGrid(users = users)
}


