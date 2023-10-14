package desidev.hango.states

interface ValueDispatch<ValueType> {
    fun dispatch(action: IAction<ValueType>)

    fun dispatch(action: IAsyncAction<ValueType>)

    fun dispatch(effect: IAsyncSideEffect<ValueType>)
}