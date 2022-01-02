package dev.mateusz1913.f1results.android.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TabBarItem(val label: String, val onPress: () -> Unit)

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
