package desidev.hango.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import desidev.hango.ui.composables.HomeTabs
import desidev.hango.ui.composables.TabConfig
import desidev.hango.ui.screens.discover.DiscoverContent
import desidev.hango.ui.screens.following.FollowingContent
import desidev.hango.ui.theme.AppTheme


@Preview(apiLevel = 33)
@Composable
fun HomeContentPreview() {
    AppTheme {
        HomeContent(component = remember { FakeHomeComponent() })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(component: HomeComponent) {
    val childTab by component.childTab.subscribeAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    HomeTabs(
                        activeChild = childTab.active.instance,
                        tabConfig = TabConfig(
                            tabColor = colorScheme.outlineVariant,
                            tabTextStyle = typography.titleSmall,
                            selectedTabColor = colorScheme.primary,
                            selectedTabTextStyle = typography.titleLarge,
                        ),
                        onDiscoverClick = {
                            component.onDiscoverTabSelect()
                        },
                        onFollowingClick = {
                            component.onFollowingTabSelect()
                        }
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }
                },
            )
        }
    ) { paddingValues ->
        Children(stack = childTab, modifier = Modifier.padding(paddingValues)) {
            when (val child = it.instance) {
                is HomeComponent.Child.DiscoverScreen -> DiscoverContent(child.component)
                is HomeComponent.Child.FollowingScreen -> FollowingContent(child.component)
            }
        }
    }
}


