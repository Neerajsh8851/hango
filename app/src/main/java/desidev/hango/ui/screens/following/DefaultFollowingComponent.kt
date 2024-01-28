package desidev.hango.ui.screens.following

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.User

class DefaultFollowingComponent(
    componentContext: ComponentContext,
    override val users: Value<List<User>>
) : FollowingComponent, ComponentContext by componentContext