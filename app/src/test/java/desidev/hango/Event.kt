package desidev.hango

interface Events<T: Any> {
    fun onEvent(e: T)
}