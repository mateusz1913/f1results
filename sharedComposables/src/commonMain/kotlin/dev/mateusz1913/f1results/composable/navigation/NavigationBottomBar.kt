package dev.mateusz1913.f1results.composable.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.composable.theme.F1ResultsColor

@Composable
fun NavigationBottomBar(
    bottomBarVisibleState: MutableState<Boolean>,
    items: List<Screen>,
    isItemSelected: (Screen) -> Boolean,
    onBottomItemClick: (Screen) -> Unit
) {
    AnimatedVisibility(
        visible = bottomBarVisibleState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        BottomNavigation(backgroundColor = F1ResultsColor.LightBottomTabsBackground) {
            items.forEach { screen ->
                val selected = isItemSelected(screen)
                BottomNavigationItem(
                    label = {
                        screen.label?.let {
                            Text(it, fontSize = 10.sp)
                        }
                    },
                    icon = {
                        screen.icon?.let {
                            Icon(it, contentDescription = screen.iconContentDescription)
                        }
                    },
                    selected = selected,
                    onClick = {
                        onBottomItemClick(screen)
                    },
                    selectedContentColor = MaterialTheme.colors.primary
                )
            }
        }
    }
}
