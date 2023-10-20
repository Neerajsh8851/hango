package desidev.hango.appstate

import desidev.hango.appstate.navigation.NavigationModel
import desidev.hango.states.StateValue


/**
 * Application Global State object
 */

class AppState(
    val navigationState: StateValue<NavigationModel>
)



