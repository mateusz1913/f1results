package dev.mateusz1913.f1results.composable.navigation

import androidx.compose.ui.graphics.vector.ImageVector

interface Screen {
    val route: String
    val icon: ImageVector?
    val iconContentDescription: String?
    val label: String?
}
