package desidev.hango.ui.navigation

import com.arkivanov.decompose.ComponentContext

class ScreenBComponent(
    componentContext: ComponentContext,
    val message: String,
    private val onGoBack : () -> Unit
) : ComponentContext by componentContext {
    fun goBack() { onGoBack() }
}
