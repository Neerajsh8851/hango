package desidev.hango.ui.screens.home

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.post
import desidev.hango.ui.screens.discover.FakeDiscoverComponent
import desidev.hango.ui.screens.following.FakeFollowingComponent

class FakeHomeComponent : HomeComponent {

    private val initialChild = Child.Created(
        configuration = HomeComponent.Config.Discover,
        instance = HomeComponent.Child.DiscoverScreen(FakeDiscoverComponent())
    )

    private val mutChildTab =
        MutableValue<ChildStack<HomeComponent.Config, HomeComponent.Child>>(ChildStack(initialChild))

    override val childTab: Value<ChildStack<HomeComponent.Config, HomeComponent.Child>> =
        mutChildTab

    override fun onDiscoverTabSelect() {
        mutChildTab.post(
            ChildStack(
                Child.Created(
                    configuration = HomeComponent.Config.Discover,
                    instance = HomeComponent.Child.DiscoverScreen(FakeDiscoverComponent())
                )
            )
        )
    }


    override fun onFollowingTabSelect() {
        mutChildTab.post(
            ChildStack(
                Child.Created(
                    configuration = HomeComponent.Config.Following,
                    instance = HomeComponent.Child.FollowingScreen(FakeFollowingComponent())
                )
            )
        )
    }
}