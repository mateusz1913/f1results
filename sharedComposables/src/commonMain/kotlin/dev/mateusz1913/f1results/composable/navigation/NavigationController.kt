package dev.mateusz1913.f1results.composable.navigation

interface NavigationController {
    fun goBack()
    fun navigate(to: String)
    fun navigateToCircuitScreen(circuitId: String)
    fun navigateToConstructorScreen(constructorId: String)
    fun navigateToDriverScreen(driverId: String)
}
