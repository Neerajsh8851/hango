package desidev.hango.ui

import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

fun LifecycleOwner.componentScope(coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()): CoroutineScope {
    return CoroutineScope(coroutineContext).also { scope ->
        lifecycle.doOnDestroy(scope::cancel)
    }
}