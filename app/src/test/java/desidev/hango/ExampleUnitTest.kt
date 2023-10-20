package desidev.hango

import desidev.hango.states.Dec
import desidev.hango.states.IAction
import desidev.hango.states.Inc
import desidev.hango.states.Message
import desidev.hango.states.StateWrapper
import desidev.hango.states.Store
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun countSlotTest() {
        val store = Store<String>()
        store.config {
            put("count") { 0 }
        }

        val ref = store.getRef<Int>("count")

        println("initial value: ${ref.getValue()}")

        ref.observe { old, new ->
            println("count value: $new")
        }

        ref.dispatch(Increment)
        ref.dispatch(Decrement)

        val count = store.getRef<Int>("count")

        ref.dispose()
        count.dispose()
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun stateWrapperTest() {
        val stateWrapper = StateWrapper<Int, Message>(0) { prev, msg ->
            when (msg) {
                is Inc -> {
                    prev + 1
                }

                is Dec -> {
                    prev - 1
                }
            }
        }

        stateWrapper.addObserver { o, n ->
            println("on update: $n")
        }

        stateWrapper.send(Inc)
    }
}




data class IncrementBy(val value: Int) : IAction<Int> {
    override fun execute(value: Int): Int {
        return value + this.value
    }
}

data object Increment : IAction<Int> {
    override fun execute(value: Int): Int = value + 1
}

data object Decrement : IAction<Int> {
    override fun execute(value: Int): Int = value - 1
}