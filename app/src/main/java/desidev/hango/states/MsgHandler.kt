package desidev.hango.states

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

interface MsgHandler<M> {
    fun send(msgType: M)
}

class StateWrapper<S, M>(
    private var _state: S,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
    private val reducer: (prev: S, msg: M) -> S
) : MsgHandler<M>, Subject<S>() {

    val state: S get() = _state
    override fun send(msgType: M): Unit {
        scope.launch {
            val oldS = state
            _state = reducer.invoke(_state, msgType)
            notify(oldS, state)
        }
    }
}

sealed class Message

data object Inc : Message()
data object Dec : Message()
