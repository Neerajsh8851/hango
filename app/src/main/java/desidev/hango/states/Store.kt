package desidev.hango.states

class Store<SlotKey> {
    private val factory = StoreFactory()
    private val slots = hashMapOf<SlotKey, StateValue<*>>()
    private val refs = hashMapOf<SlotKey, Int>()
    private fun <S : StateValue<*>> getStateValue(key: SlotKey): S {
        var slot = slots[key]
        if (slot != null) return slot as S
        slot = factory.getSlot(key) as S
        slots[key] = slot
        return slot
    }


    fun <T> getRef(key: SlotKey) = run {
        val state = getStateValue<StateValue<T>>(key)

        val ref = object : ValueReference<T> {
            val observers = mutableListOf<Observer<T>>()

            override fun getValue(): T = state.getValue()

            override fun observe(observer: Observer<T>) {
                observers.add(observer)
                state.addObserver(observer)
            }

            override fun dispose() {
                observers.forEach {
                    state.removeObserver(it)
                }

                val count = refs[key]!! - 1
                refs[key] = count
                if (count == 0) {
                    slots.remove(key).also {
                        println("Last ValueRef removed the Value on dispose")
                    }
                }
            }
        }

        refs[key] = refs[key]?.plus(1) ?: 1

        Reference(state, ref)
    }


    fun config(block: StoreFactory.() -> Unit) {
        factory.block()
    }

    inner class StoreFactory internal constructor() {
        private val factory = hashMapOf<SlotKey, () -> Any>()
        fun <R : Any> put(key: SlotKey, block: () -> R) {
            factory[key] = block
        }

        internal fun getSlot(key: SlotKey) = factory[key]?.invoke()?.let { StateValue(it) }
            ?: throw IllegalStateException("no slot were found on key $key")
    }
}