package dev.mateusz1913.f1results.composable.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.composable.common.verticalFadingEdge

@Composable
fun ExploreRow(label: String, icon: @Composable () -> Unit, onPress: () -> Unit) {
    TextButton(onClick = onPress, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label.uppercase(), fontSize = 24.sp)
            icon()
        }
    }
}

@Composable
fun ExploreScreen() {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .verticalFadingEdge(scrollState = scrollState, length = 30.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ExploreRow(
                label = "Archival races",
                icon = {
                    Icon(Icons.Rounded.SportsScore, contentDescription = "Race icon")
                },
                onPress = {}
            )
            ExploreRow(
                label = "Archival standings",
                icon = {
                    Icon(Icons.Rounded.EmojiEvents, contentDescription = "Trophy icon")
                },
                onPress = {}
            )
            ExploreRow(
                label = "Drivers",
                icon = {
                    Icon(Icons.Rounded.SportsMotorsports, contentDescription = "Driver icon")
                },
                onPress = {}
            )
            ExploreRow(
                label = "Constructors",
                icon = {
                    Icon(Icons.Rounded.Groups, contentDescription = "Constructor icon")
                },
                onPress = {}
            )
            ExploreRow(
                label = "Settings",
                icon = {
                    Icon(Icons.Rounded.Settings, contentDescription = "Settings icon")
                },
                onPress = {}
            )
        }
    }
}
