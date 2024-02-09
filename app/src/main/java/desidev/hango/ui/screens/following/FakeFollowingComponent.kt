package desidev.hango.ui.screens.following

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.User
import desidev.hango.ui.screens.discover.FakeDiscoverComponent

class FakeFollowingComponent : FollowingComponent {
    override val users: Value<List<User>> = MutableValue(FakeDiscoverComponent.dummyUsers)
    override val isLoading: Value<Boolean> = MutableValue(false)
}