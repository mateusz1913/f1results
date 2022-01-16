package dev.mateusz1913.f1results.composable.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun NavigationTopBar(
    topBarVisibleState: MutableState<Boolean>,
    title: String,
    shouldDisplayBackButton: Boolean,
    onBackButtonPress: () -> Unit
) {
    AnimatedVisibility(
        visible = topBarVisibleState.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it })
    ) {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = if (shouldDisplayBackButton) {
                {
                    IconButton(onClick = { onBackButtonPress() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            } else {
                null
            }
        )
    }
}
