package desidev.hango.states


interface ValueReference<T> {
    fun getValue(): T
    fun observe(observer: Observer<T>)
    fun dispose()
}
