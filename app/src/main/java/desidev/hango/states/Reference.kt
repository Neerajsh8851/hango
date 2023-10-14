package desidev.hango.states

class Reference<T>(private val stateValue: StateValue<T>, private val ref: ValueReference<T>) :
    ValueReference<T> by ref, ValueDispatch<T> by stateValue