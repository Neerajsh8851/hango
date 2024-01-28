package desidev.hango.ui.screens.discover

import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.User

interface DiscoverComponent {
    val users: Value<List<User>>
}