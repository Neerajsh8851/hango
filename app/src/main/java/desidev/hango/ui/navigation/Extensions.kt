package desidev.hango.ui.navigation

import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

fun LifecycleOwner.coroutineScope(coroutineContext: CoroutineContext) : CoroutineScope {
    return CoroutineScope(coroutineContext).also { scope ->
        lifecycle.doOnDestroy(scope::cancel)
    }
}