package desidev.hango.appstate.navigation

import desidev.hango.states.IAction

sealed class NavigationAction : IAction<NavigationModel> {
    data class Push(val screen: Screen) : NavigationAction() {
        override fun execute(prev: NavigationModel): NavigationModel {
            val screens = prev.list + prev.screen
            return NavigationModel(
                screen = screen,
                list = screens
            )
        }
    }

    data object Back : NavigationAction() {
        override fun execute(prev: NavigationModel): NavigationModel {
            return NavigationModel(
                screen = prev.list.last(),
                list = prev.list.dropLast(1)
            )
        }
    }

    data class Replace(val screen: Screen) : NavigationAction() {
        override fun execute(prev: NavigationModel): NavigationModel {
            return prev.copy(screen = screen)
        }
    }
}