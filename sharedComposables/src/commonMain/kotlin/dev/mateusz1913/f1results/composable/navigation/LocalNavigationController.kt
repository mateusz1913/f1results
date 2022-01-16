package dev.mateusz1913.f1results.composable.navigation

import androidx.compose.runtime.compositionLocalOf

val LocalNavController = compositionLocalOf<NavigationController> {
    object: NavigationController {
        override fun goBack() {}

        override fun navigate(to: String) {}

        override fun navigateToCircuitScreen(circuitId: String) {}

        override fun navigateToConstructorScreen(constructorId: String) {}

        override fun navigateToDriverScreen(driverId: String) {}
    }
}
