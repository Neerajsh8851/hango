package desidev.hango.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun <F, R> convert(state: StateValue<F>, block: (F) -> R): State<R> {
    val mutableState = remember { mutableStateOf(block(state.getValue())) }
    DisposableEffect(state) {
        val observer: Observer<F> = Observer { _, new ->
            mutableState.value = block(new)
        }
        state.addObserver(observer)
        onDispose {
            state.removeObserver(observer)
        }
    }

    return mutableState
}