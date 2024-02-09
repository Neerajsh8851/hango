package desidev.hango.ui.screens.discover

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import desidev.hango.api.model.User

class DefaultDiscoverComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, DiscoverComponent {

    internal data class Model(
        val users: List<User> = emptyList(),
        val isLoading: Boolean = true
    )

    private val model = MutableValue(Model())

    override val users: Value<List<User>>
        get() = model.map { it.users }


    override val isLoading: Value<Boolean> = model.map { it.isLoading }
}
