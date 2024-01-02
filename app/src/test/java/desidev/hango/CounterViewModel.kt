package desidev.hango

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class CounterViewModel(initialValue: Int) : OnCountDec, OnCountInc, OnCountReset {
    private val _count = MutableValue(initialValue)
    val count: Value<Int> = _count
    override fun inc(by: Int) {
        _count.value += by
    }

    override fun dec(by: Int) {
        _count.value -= by
    }

    override fun reset() {
        _count.value = 0
    }
}


interface OnCountInc {
    fun inc(by: Int = 0)
}

interface OnCountDec {
    fun dec(by: Int = 0)
}

interface OnCountReset {
    fun reset()
}