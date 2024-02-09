package desidev.hango.ui.screens.following

import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.User

interface FollowingComponent {
    val users : Value<List<User>>
    val isLoading : Value<Boolean>
}

