package desidev.hango.ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class ScreenAComponent(
    componentContext: ComponentContext,
    private val onNavigateToScreenB: (String) -> Unit
) : ComponentContext by componentContext {
    private val _text = MutableValue("")
    val text: Value<String> = _text


    fun onEvent(event: Event) {
        when (event) {
            is Event.UpdateText -> {
                _text.value = event.text
            }

            is Event.OnClickButtonA -> {
                onNavigateToScreenB(text.value)
            }
        }
    }

    sealed class Event {
        data class UpdateText(val text: String) : Event()
        data object OnClickButtonA : Event()
    }
}