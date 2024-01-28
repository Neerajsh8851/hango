package desidev.hango.ui.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import desidev.hango.ui.screens.home.FakeHomeComponent
import desidev.hango.ui.screens.home.HomeComponent
import desidev.hango.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Preview(apiLevel = 33)
@Composable
fun HomeTopBarPreview() {
    val homeComponent = remember { FakeHomeComponent() }
    val tabStack  by homeComponent.childTab.subscribeAsState()
    val childTab = tabStack.active.instance

    AppTheme {
        CenterAlignedTopAppBar(
            title = {
                HomeTabs(
                    activeChild = childTab,
                    tabConfig = TabConfig(
                        tabColor = colorScheme.outlineVariant,
                        tabTextStyle = typography.titleMedium,
                        selectedTabColor = colorScheme.primary,
                        selectedTabTextStyle = typography.titleLarge,
                    ),
                    onDiscoverClick = {
                        homeComponent.onDiscoverTabSelect()
                    },
                    onFollowingClick = {
                        homeComponent.onFollowingTabSelect()
                    }
                )
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
            }
        )
    }
}

data class Tab(
    val text: String,
    val onClick: () -> Unit
)


data class TabConfig(
    val tabColor: Color,
    val tabTextStyle: TextStyle,
    val selectedTabColor: Color,
    val selectedTabTextStyle: TextStyle,
    val tabSpacing: Dp = 16.dp
)

@Composable
fun HomeTabs(
    modifier: Modifier = Modifier,
    activeChild: HomeComponent.Child,
    tabConfig: TabConfig,
    onDiscoverClick: () -> Unit,
    onFollowingClick: () -> Unit
) {
    val selectedTabIndex by rememberUpdatedState(
        when (activeChild) {
            is HomeComponent.Child.DiscoverScreen -> 0
            is HomeComponent.Child.FollowingScreen -> 1
        }
    )

    val tabs = remember {
        listOf(
            Tab(text = "Discover", onClick = onDiscoverClick),
            Tab(text = "Following", onClick = onFollowingClick)
        )
    }


    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(tabConfig.tabSpacing),
    ) {
        tabs.forEachIndexed { index, tab ->
            val textStyle = if (index == selectedTabIndex) {
                tabConfig.selectedTabTextStyle.copy(
                    color = tabConfig.selectedTabColor
                )
            } else {
                tabConfig.tabTextStyle.copy(
                    color = tabConfig.tabColor
                )
            }

            val fontSize by animateFloatAsState(
                targetValue = textStyle.fontSize.value,
                label = "fontSize"
            )
            val color by animateColorAsState(targetValue = textStyle.color, label = "color")


            TabItem(
                text = tab.text,
                textStyle = textStyle.copy(
                    fontSize = if (textStyle.fontSize.isSp) fontSize.sp else fontSize.em,
                    color = color
                ),
                onClick = tab.onClick
            )
        }
    }
}

@Composable
fun RowScope.TabItem(
    text: String,
    textStyle: TextStyle,
    onClick: () -> Unit
) {
    Text(
        text = text,
        style = textStyle,
        modifier = Modifier
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .alignByBaseline()
    )
}