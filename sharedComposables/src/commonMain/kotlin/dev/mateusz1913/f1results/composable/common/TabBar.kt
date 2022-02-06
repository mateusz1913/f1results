package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TabBarItem(
    val label: String,
    val onPress: () -> Unit
)

@Composable
fun TabBar(items: Array<TabBarItem>) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)) {
        items.map { item ->
            TextButton(
                onClick = item.onPress,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(item.label, fontSize = 18.sp)
            }
        }
    }
}
