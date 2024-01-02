package desidev.hango

import com.arkivanov.decompose.value.Value

class ChildComponent(
    val count: Value<Int>,
    private val onCountReset: OnCountReset,
    private val onCountInc: OnCountInc,
    private val onCountDec: OnCountDec,
) : Events<ChildEvent> {
    override fun onEvent(e: ChildEvent) = when (e) {
        ChildEvent.Inc -> onCountInc.inc()
        ChildEvent.Dec -> onCountDec.dec()
        ChildEvent.Reset -> onCountReset.reset()
    }
}


sealed interface ChildEvent {
    data object Inc : ChildEvent
    data object Dec : ChildEvent
    data object Reset : ChildEvent
}