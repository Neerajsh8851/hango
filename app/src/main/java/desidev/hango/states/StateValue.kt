package desidev.hango.states

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class StateValue<ValueType>(
    private var value: ValueType,
) : ValueDispatch<ValueType>, Subject<ValueType>() {

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    fun getValue() = value

    override fun dispatch(action: IAction<ValueType>) {
        val old = value
        value = action.execute(value)
        notify(old, value)
    }

    override fun dispatch(action: IAsyncAction<ValueType>) {
        scope.launch {
            val old = value
            value = action.asyncExecute(value)
            notify(old, value)
        }
    }

    override fun dispatch(effect: IAsyncSideEffect<ValueType>) {
        scope.launch {
            effect.execute(this@StateValue)
        }
    }
}