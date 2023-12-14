package desidev.hango.ui.screens

interface Component<E> {
    fun onEvent(event: E)
}