package desidev.hango.ui.screens.home

import android.os.Parcelable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.screens.discover.DiscoverComponent
import desidev.hango.ui.screens.following.FollowingComponent
import kotlinx.parcelize.Parcelize

interface HomeComponent {
    fun onDiscoverTabSelect()
    fun onFollowingTabSelect()

    val childTab: Value<ChildStack<Config, Child>>
    @Parcelize
    sealed interface Config: Parcelable {
        @Parcelize
        data object Discover: Config
        @Parcelize
        data object Following: Config
    }

    sealed interface Child {
        data class DiscoverScreen(val component: DiscoverComponent): Child
        data class FollowingScreen(val component: FollowingComponent): Child
    }
}