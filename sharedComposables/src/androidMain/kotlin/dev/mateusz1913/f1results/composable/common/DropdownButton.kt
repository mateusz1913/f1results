package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
actual fun DropdownButton(items: List<String>, selected: String, onSelectedChange: (newSelected: String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { expanded = !expanded }) {
            Text(selected)
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.sizeIn(maxHeight = 300.dp)
        ) {
            items.map { item ->
                DropdownMenuItem(onClick = {
                    onSelectedChange(item)
                    expanded = false
                }) {
                    Text(item)
                }
            }
        }
    }
}
