package desidev.kotlin.utils

sealed interface Option<out T : Any> {
    data class Some<T : Any>(val value: T) : Option<T>
    data object None : Option<Nothing>
}


fun <T : Any> Option<T>.isSome() = this is Option.Some<T>
fun <T : Any> Option<T>.isNone() = this is Option.None


/**
 * Returns the option if it is Some, otherwise returns the other option.
 */
fun <T : Any> Option<T>.or(other: Option<T>): Option<T> {
    return if (this is Option.Some) this else other
}

fun <T : Any> Option<T>.asSome() = this as Option.Some<T>

fun <T : Any> Option<T>.unwrap() = this.asSome().value

inline fun <T : Any, R : Any> Option<T>.runIfSome(block: (T) -> Option<R>): Option<R> {
    return when (this) {
        is Option.Some -> block.invoke(value)
        else -> Option.None
    }
}

inline fun <T : Any, R : Any> Option<T>.runIfNone(block: () -> Option<R>): Option<R> {
    return when (this) {
        is Option.None -> block.invoke()
        else -> Option.None
    }
}


inline fun <T : Any> Option<T>.ifSome(block: (T) -> Unit) {
    if (isSome()) { block(unwrap()) }
}

inline fun <T : Any> Option<T>.ifNone(block: () -> Unit) {
    if (isNone()) { block() }
}



