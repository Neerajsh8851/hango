package desidev.hango

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

}


class Root {
    private val counterModel = CounterViewModel(1)
    fun root() {
        val root = ParentComponent(
            count = counterModel.count,
            onCountReset = counterModel,
            onCountDec = counterModel,
            onCountInc = counterModel
        )
    }
}