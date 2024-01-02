package desidev.hango.ui.screens

/**
 * This interface outlines a set of user events that can be triggered by the UI content.
 * Implementing components will define their specific events using the generic type parameter E.
 *
 * The purpose of this interface is to establish a contract for handling user interactions within the app.
 * Implementers must provide an implementation for the `onEvent` method, specifying the type of events they expect to receive.
 *
 * Example Usage:
 * ```
 * class MyComponent : Events<MyEventType> {
 *     override fun onEvent(event: MyEventType) {
 *         // Handle the specific event logic here
 *     }
 * }
 * ```
 *
 * @param E The type parameter representing the specific events that will be handled.
 */
interface Events<E> {
    /**
     * Invoked when a user event occurs. Implementers should provide logic
     * to handle the specific event defined by the generic type E.
     *
     * @param e The instance of the event to be processed.
     */
    fun sendEvent(e: E)
}
