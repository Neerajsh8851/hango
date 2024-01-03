package desidev.kotlin.utils

sealed interface Option<out T : Any> {
    data class Some<T : Any>(val value: T) : Option<T>
    data object None : Option<Nothing>
}


fun <T : Any> Option<T>.isSome() = this is Option.Some<T>
fun <T : Any> Option<T>.isNone() = this is Option.None

fun <T : Any> Option<T>.or(other: Option<T>): Option<T> {
    return if (this is Option.Some) this else other
}

fun <T : Any> Option<T>.asSome() = this as Option.Some<T>

fun <T : Any> Option<T>.unwrap() = this.asSome().value

inline fun <T : Any, R : Any> Option<T>.ifSome(block: (T) -> R): Option<R> {
    return when (this) {
        is Option.Some -> Option.Some(block.invoke(value))
        else -> Option.None
    }
}

inline fun <T : Any, R : Any> Option<T>.ifNone(block: () -> R): Option<R> {
    return when (this) {
        is Option.None -> Option.Some(block())
        else -> Option.None
    }
}

