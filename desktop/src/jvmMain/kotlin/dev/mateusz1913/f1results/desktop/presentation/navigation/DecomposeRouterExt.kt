package dev.mateusz1913.f1results.desktop.presentation.navigation

import com.arkivanov.decompose.router.Router
import com.arkivanov.essenty.parcelable.Parcelable

inline fun <C : Parcelable> Router<C, *>.navigateInBottomNavigation(configuration: C) {
    navigate { stack ->
        val oldConfig: C? = stack.find { it::class == configuration::class }
        if (oldConfig != null) {
            (stack - oldConfig) + oldConfig
        } else {
            stack + configuration
        }
    }
}
