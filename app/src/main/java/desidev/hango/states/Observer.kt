package desidev.hango.states

fun interface Observer<T> {
    fun update(old: T, new : T)
}