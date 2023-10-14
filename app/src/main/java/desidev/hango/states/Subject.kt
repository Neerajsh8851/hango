package desidev.hango.states


abstract class Subject<T> {
    private val observers: MutableList<Observer<T>> = mutableListOf()

    fun addObserver(observer: Observer<T>) {
        observers.add(observer)
    }

    fun removeObserver(observer: Observer<T>) {
        observers.remove(observer)
    }

    fun notify(old: T, new: T) {
        observers.forEach { observer ->
            observer.update(old, new)
        }
    }
}