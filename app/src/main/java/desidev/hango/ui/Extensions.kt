package desidev.hango.ui

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

fun LifecycleOwner.componentScope(
    coroutineContext: CoroutineContext = Dispatchers.Main,
    job: Job = SupervisorJob()
): CoroutineScope {
    return CoroutineScope(coroutineContext + job).also { scope ->
        lifecycle.doOnDestroy(scope::cancel)
    }
}

/**
 * Post a new value to the [MutableValue]
 */
fun <T : Any> MutableValue<T>.post(new: T) {
    value = new
}

