package desidev.hango

import com.arkivanov.decompose.value.Value

class ParentComponent(
    private val count: Value<Int>,
    private val onCountReset: OnCountReset,
    private val onCountInc: OnCountInc,
    private val onCountDec: OnCountDec,
) {
    fun child() {
        ChildComponent(
            count = count,
            onCountReset = onCountReset,
            onCountDec = onCountDec,
            onCountInc = onCountInc
        )
    }
}