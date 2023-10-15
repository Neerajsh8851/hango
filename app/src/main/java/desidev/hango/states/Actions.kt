package desidev.hango.states

fun interface IAsyncSideEffect<ValueType> {
    suspend fun execute(state: ValueType, dispatch: ValueDispatch<ValueType>)
}

fun interface IAction<ValueType> {
    fun execute(value: ValueType): ValueType
}

fun interface IAsyncAction<StateType> {
    suspend fun asyncExecute(param: StateType): StateType
}






